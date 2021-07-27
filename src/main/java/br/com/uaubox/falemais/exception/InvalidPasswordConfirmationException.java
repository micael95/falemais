package br.com.uaubox.falemais.exception;

public class InvalidPasswordConfirmationException extends Exception {
    private String code = "invalid-password-confirmation";

    public InvalidPasswordConfirmationException() {
        super("Invalid password confirmation");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
