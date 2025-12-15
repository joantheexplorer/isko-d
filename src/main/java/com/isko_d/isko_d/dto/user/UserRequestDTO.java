package com.isko_d.isko_d.dto.user;

import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public class UserRequestDTO {

    @NotBlank(groups=Create.class)
    private String firstName;

    @NotBlank(groups=Create.class)
    private String lastName;

    @NotBlank(groups=Create.class)
    @Email
    private String email;

    @NotBlank(groups=Create.class)
    private String password;

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
