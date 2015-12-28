package com.alexstyl.gif.util;

public class DeveloperError extends RuntimeException {

    private DeveloperError() {
    }

    public DeveloperError(String message) {
        super(message);
    }
}
