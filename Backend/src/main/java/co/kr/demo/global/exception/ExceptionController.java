package co.kr.demo.global.exception;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleRunTimeException(RuntimeException ex, NativeWebRequest request) {
        if (ex instanceof NotFoundException) {
            NotFoundException notFoundException = (NotFoundException) ex;

            return new ResponseEntity<>(
                    ErrorResponseDto.builder().ErrorCode(notFoundException.getErrorCode().getErrorCode()).Message(notFoundException.getErrorCode().getMessage()).build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                ErrorResponseDto.builder().Message(ex.getMessage()).build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
