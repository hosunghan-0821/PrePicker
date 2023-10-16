package co.kr.demo.global.error;


import co.kr.demo.global.error.dto.ErrorCode;
import co.kr.demo.global.error.dto.ErrorResponseDetailDto;
import co.kr.demo.global.error.dto.ErrorResponseDto;
import co.kr.demo.global.error.exception.NotFoundException;
import co.kr.demo.infra.discord.DiscordBot;
import co.kr.demo.infra.discord.DiscordChannel;
import co.kr.demo.infra.discord.EDiscordChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionController {


    private final static String PACKAGE_NAME = "co.kr.demo";
    private final DiscordChannel discordChannel;
    /*
     * TO-DO
     * AOP를 통해서 Exception 잡던지, 각 Excpetion 캐치하는 곳에서 쏘는걸 잡던지.. 어쩃든 모니터링이 필요하다.
     * */

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleRunTimeException(RuntimeException ex, NativeWebRequest request) {
        makeErrorMessageToDiscordChannel(ex);

        if (ex instanceof NotFoundException) {
            NotFoundException notFoundException = (NotFoundException) ex;

            return new ResponseEntity<>(
                    ErrorResponseDto.builder().ErrorCode(notFoundException.getErrorCode().getErrorCode()).Message(notFoundException.getErrorCode().getDefaultMessage()).build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                ErrorResponseDto.builder().Message(ex.getMessage()).build(),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler
    public ResponseEntity<? extends ErrorResponseDto> HandleMethodArgumentNotValidException(MethodArgumentNotValidException ex, NativeWebRequest request) {

        final List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        ErrorResponseDetailDto errorResponseDetailDto = ErrorResponseDetailDto.builder()
                .ErrorCode(ErrorCode.INVALID_ARGUMENT_EXCEPTION.getErrorCode())
                .Message(ErrorCode.INVALID_METHOD_ARGUMENT.getDefaultMessage())
                .errorDetailList(new ArrayList<>())
                .build();

        if (ex.getBindingResult().hasFieldErrors()) {
            for (ObjectError objectError : allErrors) {

                final String errorMessage = objectError.toString();
                final String field = errorMessage.substring(errorMessage.indexOf("on field") + 8, errorMessage.indexOf(":", errorMessage.indexOf("on field")));

                errorResponseDetailDto.addErrorDetail(objectError.getObjectName(), objectError.getDefaultMessage(), field);
            }
        }

        makeErrorMessageToDiscordChannel(ex);

        return new ResponseEntity<>(
                errorResponseDetailDto,
                HttpStatus.BAD_REQUEST
        );

    }

    private void makeErrorMessageToDiscordChannel(Exception ex) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTrace = ex.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (stackTraceElement.toString().startsWith(PACKAGE_NAME)) {
                sb.append(stackTraceElement);
                sb.append("\n");
                sb.append("  ");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);


        String errorMessagePrefix = "[에러발생] \n";
        String message = errorMessagePrefix + "\n" + "stackTrace = {\n" + "  " + sb.toString() + "}";
        discordChannel.sendMessageToChannel(EDiscordChannel.SERVER_ERROR, message);
    }
}
