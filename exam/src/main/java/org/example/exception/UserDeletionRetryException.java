package org.example.exception;

public class UserDeletionRetryException extends RuntimeException {
    public UserDeletionRetryException(String message, Throwable cause) {
        super(message, cause);
    }
}
