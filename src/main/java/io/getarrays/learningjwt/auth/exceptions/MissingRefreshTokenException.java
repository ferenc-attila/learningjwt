package io.getarrays.learningjwt.auth.exceptions;

public class MissingRefreshTokenException extends RuntimeException {

    public MissingRefreshTokenException() {
        super("Refresh token is missing!");
    }
}
