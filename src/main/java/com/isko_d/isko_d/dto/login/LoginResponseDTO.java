package com.isko_d.isko_d.dto.login;

import com.isko_d.isko_d.model.User;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class LoginResponseDTO {
    private Long id;
    private String email;
    private Set<String> roles = new HashSet<>();
    private String plainToken;

    public LoginResponseDTO(User user, String plainToken) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet());
        this.plainToken = plainToken;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public Set<String> getRoles() { return roles; }
    public String getPlainToken() { return plainToken; }
}
