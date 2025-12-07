package com.isko_d.isko_d.dto.department;

import com.isko_d.isko_d.model.Department;
import java.time.LocalDateTime;

public class DepartmentResponseDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DepartmentResponseDTO(Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.createdAt = department.getCreatedAt();
        this.updatedAt = department.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
