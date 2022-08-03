package io.getarrays.learningjwt.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class InventoryAppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @Column(unique = true, nullable = false, length = 254)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<InventoryAppRole> roles = new ArrayList<>();

    public InventoryAppUser(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void addRole(InventoryAppRole role) {
        this.roles.add(role);
    }
}
