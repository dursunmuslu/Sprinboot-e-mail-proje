package com.hoaxify.ws.user.exception;

public class InvalidTokenException extends RuntimeException {
    public String InvalidTokenException() {
        return "Invalid activation tekon";
    }
}
