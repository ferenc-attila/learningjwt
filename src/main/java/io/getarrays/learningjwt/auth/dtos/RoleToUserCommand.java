package io.getarrays.learningjwt.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleToUserCommand {

    private String username;

    private String nameOfRole;
}
