package io.getarrays.learningjwt.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InventoryAppUserDetails {

    private Long id;

    private String name;

    private String username;

    private String email;
}
