package com.isko_d.isko_d.dto.user;

import com.isko_d.isko_d.dto.role.RoleResponseDTO;
import com.isko_d.isko_d.model.User;
import java.time.LocalDateTime;

public class UserResponseDTO {
    private Long id;
    private String barcode;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private RoleResponseDTO role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.barcode = user.getBarcode();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = new RoleResponseDTO(user.getRole());
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getBarcode() { return barcode; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public RoleResponseDTO getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
