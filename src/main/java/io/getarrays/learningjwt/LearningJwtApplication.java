package io.getarrays.learningjwt;

import io.getarrays.learningjwt.auth.dtos.CreateInventoryAppRoleCommand;
import io.getarrays.learningjwt.auth.dtos.CreateInventoryAppUserCommand;
import io.getarrays.learningjwt.auth.dtos.RoleToUserCommand;
import io.getarrays.learningjwt.auth.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LearningJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningJwtApplication.class, args);
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner run(UserRoleService service) {
        return args -> {
            service.saveRole(new CreateInventoryAppRoleCommand("ROLE_USER"));
            service.saveRole(new CreateInventoryAppRoleCommand("ROLE_MANAGER"));
            service.saveRole(new CreateInventoryAppRoleCommand("ROLE_ADMIN"));
            service.saveRole(new CreateInventoryAppRoleCommand("ROLE_SUPER_ADMIN"));

            service.saveUser(new CreateInventoryAppUserCommand("John Doe", "johndoe", "johndoe@mail.com", "johnpassword"));
            service.saveUser(new CreateInventoryAppUserCommand("Jane Doe", "janedoe", "janedoe@mail.com", "janepassword"));
            service.saveUser(new CreateInventoryAppUserCommand("Jill Doe", "jilldoe", "jilldoe@mail.com", "jillpassword"));
            service.saveUser(new CreateInventoryAppUserCommand("Jack Doe", "jackdoe", "jackdoe@mail.com", "jackpassword"));

            service.addRoleToUser(new RoleToUserCommand("johndoe", "ROLE_USER"));
            service.addRoleToUser(new RoleToUserCommand("johndoe", "ROLE_MANAGER"));
            service.addRoleToUser(new RoleToUserCommand("janedoe", "ROLE_MANAGER"));
            service.addRoleToUser(new RoleToUserCommand("jilldoe", "ROLE_ADMIN"));
            service.addRoleToUser(new RoleToUserCommand("jackdoe", "ROLE_USER"));
            service.addRoleToUser(new RoleToUserCommand("jackdoe", "ROLE_SUPER_ADMIN"));
            service.addRoleToUser(new RoleToUserCommand("jackdoe", "ROLE_ADMIN"));
        };
    }
}
