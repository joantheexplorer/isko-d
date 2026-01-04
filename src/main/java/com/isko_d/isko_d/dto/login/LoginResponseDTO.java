package com.isko_d.isko_d.dto.login;

import com.isko_d.isko_d.model.User;

public class LoginResponseDTO {
    private Long id;
    private String email;
    private String role;
    private String plainToken;

    public LoginResponseDTO(User user, String plainToken) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole().getName();
        this.plainToken = plainToken;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getPlainToken() { return plainToken; }
}
