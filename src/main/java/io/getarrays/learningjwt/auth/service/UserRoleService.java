package io.getarrays.learningjwt.auth.service;

import io.getarrays.learningjwt.auth.dtos.*;
import io.getarrays.learningjwt.auth.exceptions.RoleNotFoundException;
import io.getarrays.learningjwt.auth.exceptions.UserNotFoundException;
import io.getarrays.learningjwt.auth.mappers.InventoryAppRoleMapper;
import io.getarrays.learningjwt.auth.mappers.InventoryAppUserMapper;
import io.getarrays.learningjwt.auth.model.InventoryAppRole;
import io.getarrays.learningjwt.auth.model.InventoryAppUser;
import io.getarrays.learningjwt.auth.repository.RoleRepository;
import io.getarrays.learningjwt.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class UserRoleService implements UserDetailsService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private InventoryAppUserMapper userMapper;

    private InventoryAppRoleMapper roleMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InventoryAppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    public InventoryAppUserDetails saveUser(CreateInventoryAppUserCommand command) {
        InventoryAppUser user = new InventoryAppUser(
                command.getName(),
                command.getUsername(),
                command.getEmail(),
                passwordEncoder.encode(command.getPassword()));
        userRepository.save(user);
        log.info("Saving new user to the database: {}", user.getUsername());
        return userMapper.toInventoryAppUserDetails(user);
    }

    public InventoryAppRoleDetails saveRole(CreateInventoryAppRoleCommand command) {
        InventoryAppRole role = new InventoryAppRole(command.getName());
        roleRepository.save(role);
        log.info("Saving new role to the database: {}", role.getName());
        return roleMapper.toInventoryAppRoleDetails(role);
    }

    public InventoryAppUserDetails addRoleToUser(RoleToUserCommand command) {
        InventoryAppUser user = userRepository.findByUsername(command.getUsername())
                .orElseThrow(() -> new UserNotFoundException(command.getUsername()));
        InventoryAppRole role = roleRepository.findByName(command.getNameOfRole())
                .orElseThrow(() -> new RoleNotFoundException(command.getNameOfRole()));
        user.addRole(role);
        log.info("Adding new role, {} to user {}", role.getName(), user.getUsername());
        return userMapper.toInventoryAppUserDetails(user);
    }

    public InventoryAppUserDetails getUser(String username) {
        log.info("Fetching user {}", username);
        InventoryAppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return userMapper.toInventoryAppUserDetails(user);
    }

    public List<InventoryAppUserDetails> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(user -> userMapper.toInventoryAppUserDetails(user))
                .toList();
    }
}
