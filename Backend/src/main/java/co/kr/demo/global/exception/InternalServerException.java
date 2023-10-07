package co.kr.demo.global.exception;

public class InternalServerException extends RuntimeException{


    private ErrorCode errorCode;

    public InternalServerException() {
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public InternalServerException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
