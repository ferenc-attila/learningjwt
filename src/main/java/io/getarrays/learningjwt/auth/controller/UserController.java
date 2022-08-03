package io.getarrays.learningjwt.auth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.getarrays.learningjwt.auth.dtos.*;
import io.getarrays.learningjwt.auth.exceptions.MissingRefreshTokenException;
import io.getarrays.learningjwt.auth.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class UserController {

    private UserRoleService service;

    @GetMapping("/users")
    public List<InventoryAppUserDetails> getUsers() {
        return service.getUsers();
    }

    @PostMapping("/users/save")
    @ResponseStatus(value = HttpStatus.CREATED)
    public InventoryAppUserDetails saveUser(@Valid @RequestBody CreateInventoryAppUserCommand command) {
        return service.saveUser(command);
    }

    @PostMapping("/roles/save")
    @ResponseStatus(value = HttpStatus.CREATED)
    public InventoryAppRoleDetails saveRole(@Valid @RequestBody CreateInventoryAppRoleCommand command) {
        return service.saveRole(command);
    }

    @PostMapping("/roles/add-to-user")
    public InventoryAppUserDetails addRoleToUser(@Valid @RequestBody RoleToUserCommand command) {
        return service.addRoleToUser(command);
    }

    @GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                InventoryAppUserDetails user = service.getUser(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", service.loadUserByUsername(username).getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (IndexOutOfBoundsException | IllegalArgumentException | JWTVerificationException | IOException exc) {
                response.setHeader("error", exc.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exc.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new MissingRefreshTokenException();
        }
    }
}
