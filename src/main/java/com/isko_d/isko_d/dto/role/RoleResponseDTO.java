package com.isko_d.isko_d.dto.role;

import com.isko_d.isko_d.model.Role;
import java.time.LocalDateTime;

public class RoleResponseDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoleResponseDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.createdAt = role.getCreatedAt();
        this.updatedAt = role.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
