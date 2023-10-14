package co.kr.demo.global.error;


import co.kr.demo.global.error.dto.ErrorCode;
import co.kr.demo.global.error.dto.ErrorResponseDetailDto;
import co.kr.demo.global.error.dto.ErrorResponseDto;
import co.kr.demo.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleRunTimeException(RuntimeException ex, NativeWebRequest request) {
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
    public ResponseEntity<ErrorResponseDetailDto> HandleMethodArgumentNotValidException(MethodArgumentNotValidException ex, NativeWebRequest request) {

        final List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        ErrorResponseDetailDto errorResponseDetailDto = ErrorResponseDetailDto.builder()
                .ErrorCode(ErrorCode.INVALID_ARGUMENT_EXCEPTION.getErrorCode())
                .Message(ErrorCode.INVALID_METHOD_ARGUMENT.getDefaultMessage())
                .errorDetailList(new ArrayList<>())
                .build();

        if (ex.getBindingResult().hasFieldErrors()) {
            for (ObjectError objectError : allErrors) {
                errorResponseDetailDto.addErrorDetail(objectError.getObjectName(), objectError.getDefaultMessage());
            }
        }

        return new ResponseEntity<>(
                errorResponseDetailDto,
                HttpStatus.BAD_REQUEST
        );

    }
}
