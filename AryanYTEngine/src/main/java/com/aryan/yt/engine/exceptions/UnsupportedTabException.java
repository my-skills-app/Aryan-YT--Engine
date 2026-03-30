package com.aryan.yt.engine.exceptions;

public final class UnsupportedTabException extends UnsupportedOperationException {
    public UnsupportedTabException(final String unsupportedTab) {
        super("Unsupported tab " + unsupportedTab);
    }
}
