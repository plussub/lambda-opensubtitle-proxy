package com.plussub.opensubtitle.proxy;

/**
 * Created by stefa on 05.03.2017.
 */
public class InvalidSearchRequestException extends RuntimeException {
    public InvalidSearchRequestException() {
    }

    public InvalidSearchRequestException(String message) {
        super(message);
    }

    public InvalidSearchRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSearchRequestException(Throwable cause) {
        super(cause);
    }

    public InvalidSearchRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
