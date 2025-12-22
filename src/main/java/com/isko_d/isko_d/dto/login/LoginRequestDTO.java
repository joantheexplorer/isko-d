package com.isko_d.isko_d.dto.login;

import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank(groups=Create.class)
    private String email;

    @NotBlank(groups=Create.class)
    private String password;

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
