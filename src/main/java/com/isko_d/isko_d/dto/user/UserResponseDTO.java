package com.isko_d.isko_d.dto.user;

import com.isko_d.isko_d.dto.role.RoleResponseDTO;
import com.isko_d.isko_d.model.User;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Set<RoleResponseDTO> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.lastName = user.getLastName();
        this.roles = user.getRoles().stream().map(RoleResponseDTO::new).collect(Collectors.toSet());
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public Set<RoleResponseDTO> getRoles() { return roles; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
