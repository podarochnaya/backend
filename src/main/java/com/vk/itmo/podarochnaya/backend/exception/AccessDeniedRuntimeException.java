package com.vk.itmo.podarochnaya.backend.exception;

import java.io.Serial;

public class AccessDeniedRuntimeException extends RuntimeException {
    public AccessDeniedRuntimeException(final String message) {
        super(message);
    }
}
