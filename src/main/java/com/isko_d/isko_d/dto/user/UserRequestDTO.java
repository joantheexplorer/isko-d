package com.isko_d.isko_d.dto.user;

import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import java.util.HashSet;
import java.util.Set;

public class UserRequestDTO {

    @NotBlank(groups=Create.class)
    private String firstName;

    private String middleName;

    @NotBlank(groups=Create.class)
    private String lastName;

    @NotBlank(groups=Create.class)
    @Email
    private String email;

    @NotBlank(groups=Create.class)
    private String password;

    private Set<Long> roles = new HashSet<>();

    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Set<Long> getRoles() { return roles; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRoles(Set<Long> roles) { this.roles = roles; }
}
