package br.com.uaubox.falemais.exception;

public class InvalidPasswordConfirmationException extends Exception{

    public InvalidPasswordConfirmationException(String message) { super(message); }

    public InvalidPasswordConfirmationException() {}
}
