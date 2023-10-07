package co.kr.demo.global.exception;

public enum ErrorCode {

    NOT_FOUND_EXCEPTION("요청한 리소스를 찾지 못하였습니다.","404000"),
    NOT_FOUND_EXCEPTION_PRODUCT("요청한 상품을 찾지 못하였습니다.","404002"),
    NOT_FOUND_EXCEPTION_OPTION("요청한 옵션을 찾지 못하였습니다.","404003"),

    INTERNAL_SERVER_ERROR("서버 내부 오류","500000")
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
