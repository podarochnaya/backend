package com.vk.itmo.podarochnaya.backend.exception;

public class AccessDeniedRuntimeException extends RuntimeException {
    public AccessDeniedRuntimeException(final String message) {
        super(message);
    }
}
