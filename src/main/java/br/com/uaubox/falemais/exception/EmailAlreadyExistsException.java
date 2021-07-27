package br.com.uaubox.falemais.exception;

public class EmailAlreadyExistsException extends Exception{
    private String code = "email-already-registered";

    public EmailAlreadyExistsException() {
            super("Email already registered");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
