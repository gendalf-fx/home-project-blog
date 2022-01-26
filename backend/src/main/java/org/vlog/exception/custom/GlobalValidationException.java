package org.vlog.exception.custom;

import org.springframework.stereotype.Component;


public class GlobalValidationException extends RuntimeException{

    public GlobalValidationException(String message) {
        super(message);
    }
}
