package co.kr.demo.global.exception;

public class NotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public NotFoundException() {
        this.errorCode = ErrorCode.NOT_FOUND_EXCEPTION;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public NotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
