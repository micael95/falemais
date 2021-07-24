package br.com.uaubox.falemais.exception;

public class InvalidPasswordConfirmationException extends Exception {
    private String message;
    private String code;

    public InvalidPasswordConfirmationException(String message) {
        super(message);
    }

    public InvalidPasswordConfirmationException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public InvalidPasswordConfirmationException() {
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
