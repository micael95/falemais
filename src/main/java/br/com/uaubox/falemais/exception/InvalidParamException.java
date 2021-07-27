package br.com.uaubox.falemais.exception;

public class InvalidParamException extends Exception {
    private String message;
    private String code = "invalid-param-error";

    public InvalidParamException(String parameter) {
        super("Invalid param error: " + parameter);
    }


    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
