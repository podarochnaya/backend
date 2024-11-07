package com.vk.itmo.podarochnaya.backend.exception;

public class GiftImageException extends RuntimeException {
    public GiftImageException(final String message) {
        super(message);
    }

    public GiftImageException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
