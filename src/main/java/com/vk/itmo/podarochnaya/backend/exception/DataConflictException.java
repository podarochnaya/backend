package com.vk.itmo.podarochnaya.backend.exception;

public class DataConflictException extends RuntimeException {
    public DataConflictException(final String message) {
        super(message);
    }
}
