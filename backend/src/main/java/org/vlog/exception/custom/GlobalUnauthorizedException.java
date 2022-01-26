package org.vlog.exception.custom;

public class GlobalUnauthorizedException extends RuntimeException {

    public GlobalUnauthorizedException(String message) {
        super(message);
    }
}
