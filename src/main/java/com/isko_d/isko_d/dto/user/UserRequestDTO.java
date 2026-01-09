package com.isko_d.isko_d.dto.user;

import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public class UserRequestDTO {

    private String barcode;

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

    @NotBlank(groups=Create.class)
    private Long roleId;

    public String getBarcode() { return barcode; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Long getRoleId() { return roleId; }

    public void setBarcode(String barcode) { this.barcode = barcode; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }
}
