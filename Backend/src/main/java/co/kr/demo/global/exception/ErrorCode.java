package co.kr.demo.global.exception;

public enum ErrorCode {

    NOT_FOUND_EXCEPTION("요청한 리소스를 찾지 못하였습니다.","404001"),
    NOT_FOUND_EXCEPTION_PRODUCT("요청한 상품을 찾지 못하였습니다.","404002")
    ;
    private String message;
    private String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    ErrorCode(String message,String errorCode) {
        this.message = message;
        this.errorCode= errorCode;
    }

    public String getMessage() {
        return message;
    }
    public String getErrorCode(){
        return errorCode;
    }
}
