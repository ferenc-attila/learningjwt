package io.getarrays.learningjwt.auth.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class UserNotFoundException extends AbstractThrowableProblem {
    public UserNotFoundException(String username) {
        super(URI.create("users/not-found"),
                "User not found",
                Status.NOT_FOUND,
                String.format("User not found with username: %s", username));
    }
}
