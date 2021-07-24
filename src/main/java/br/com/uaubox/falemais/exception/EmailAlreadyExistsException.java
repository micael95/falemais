package br.com.uaubox.falemais.exception;

public class EmailAlreadyExistsException extends Exception{
    private String message;
    private String code;

    public EmailAlreadyExistsException(String message) { super(message); }

    public EmailAlreadyExistsException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public EmailAlreadyExistsException() {}

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
