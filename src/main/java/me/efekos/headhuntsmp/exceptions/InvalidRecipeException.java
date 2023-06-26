package me.efekos.headhuntsmp.exceptions;

public class InvalidRecipeException extends Exception{
    public InvalidRecipeException() {
    }

    public InvalidRecipeException(String message) {
        super(message);
    }

    public InvalidRecipeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRecipeException(Throwable cause) {
        super(cause);
    }

    public InvalidRecipeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
