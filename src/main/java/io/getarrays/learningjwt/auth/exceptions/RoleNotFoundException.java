package io.getarrays.learningjwt.auth.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class RoleNotFoundException extends AbstractThrowableProblem {
    public RoleNotFoundException(String roleName) {
        super(URI.create("roles/not-found"),
                "Role not found",
                Status.NOT_FOUND,
                String.format("Role not found with name: %s", roleName));
    }
}
