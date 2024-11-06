package com.vk.itmo.podarochnaya.backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(final Exception exception) {
        logger.error("Unknown error occurred", exception);
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException exception) {
        logger.warn(exception.getMessage(), exception);
        return buildErrorResponse(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException exception) {
        logger.warn(exception.getMessage(), exception);
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUsernameNotFoundException(final UsernameNotFoundException exception) {
        logger.warn(exception.getMessage(), exception);
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotFoundException(final NotFoundException exception) {
        logger.warn(exception.getMessage(), exception);
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> buildErrorResponse(
            final Exception exception,
            final HttpStatus httpStatus
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.name(),
                exception.getMessage()
        );
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
