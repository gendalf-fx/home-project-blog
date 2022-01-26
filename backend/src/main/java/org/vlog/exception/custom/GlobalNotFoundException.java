package org.vlog.exception.custom;

public class GlobalNotFoundException extends RuntimeException{
    public GlobalNotFoundException(String message) {
        super(message);
    }
}
