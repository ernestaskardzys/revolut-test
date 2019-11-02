package info.ernestas.revoluttest.model;

public class ExceptionDto {

    private final int errorCode;
    private final String message;

    public ExceptionDto() {
        errorCode = 0;
        message = null;
    }

    public ExceptionDto(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
