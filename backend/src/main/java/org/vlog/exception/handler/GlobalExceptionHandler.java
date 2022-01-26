package org.vlog.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.vlog.exception.custom.*;
import org.vlog.exception.model.Error;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Error> unknownError(RuntimeException ex) {
        Error error = new Error(503, "The unknown error appeared. More information: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = GlobalValidationException.class)
    public ResponseEntity<Error> validation(GlobalValidationException ex) {
        Error error = new Error(400, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = GlobalUnauthorizedException.class)
    public ResponseEntity<Error> unauthorized(GlobalUnauthorizedException ex) {
        Error error = new Error(401, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = GlobalNotFoundException.class)
    public ResponseEntity<Error> notFound(GlobalNotFoundException ex) {
        Error error = new Error(404, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = GlobalNoContentResponseException.class)
    public ResponseEntity<Error> noContentResponse(GlobalNoContentResponseException ex) {
        Error error = new Error(204, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = GlobalForbiddenException.class)
    public ResponseEntity<Error> forbiddenException(GlobalForbiddenException ex) {
        Error error = new Error(403, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
