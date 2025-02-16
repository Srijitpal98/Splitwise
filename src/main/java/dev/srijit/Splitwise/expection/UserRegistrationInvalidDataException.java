package dev.srijit.Splitwise.expection;

public class UserRegistrationInvalidDataException extends RuntimeException {
    public UserRegistrationInvalidDataException() {
    }

    public UserRegistrationInvalidDataException(String message) {
        super(message);
    }

    public UserRegistrationInvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserRegistrationInvalidDataException(Throwable cause) {
        super(cause);
    }

    public UserRegistrationInvalidDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
