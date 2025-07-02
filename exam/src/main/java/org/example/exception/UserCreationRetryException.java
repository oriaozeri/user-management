package org.example.exception;

public class UserCreationRetryException extends RuntimeException {
    public UserCreationRetryException(String message, Throwable cause) {
        super(message, cause);
    }
}
