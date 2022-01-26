package org.vlog.exception.custom;

public class GlobalNoContentResponseException extends RuntimeException{
    public GlobalNoContentResponseException(String message) {
        super(message);
    }
}
