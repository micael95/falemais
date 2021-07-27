package br.com.uaubox.falemais.exception;

public class InvalidParamException extends Exception {
    private String code = "invalid-param-error";

    public InvalidParamException(String parameter) {
        super("Invalid param error: " + parameter);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
