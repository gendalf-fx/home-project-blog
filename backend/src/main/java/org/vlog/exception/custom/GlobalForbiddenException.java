package org.vlog.exception.custom;

public class GlobalForbiddenException extends RuntimeException{
    public GlobalForbiddenException(String message) {
        super(message);
    }
}
