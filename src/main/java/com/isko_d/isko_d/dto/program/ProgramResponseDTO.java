package com.isko_d.isko_d.dto.program;

import com.isko_d.isko_d.model.Program;
import java.time.LocalDateTime;

public class ProgramResponseDTO {
    private Long id;
    private String name;
    private Long departmentId;
    private String departmentName; 
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProgramResponseDTO(Program program) {
        this.id = program.getId();
        this.name = program.getName();
        this.departmentId = program.getDepartment().getId();
        this.departmentName = program.getDepartment().getName();
        this.createdAt = program.getCreatedAt();
        this.updatedAt = program.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getDepartmentId() { return departmentId; }
    public String getDepartmentName() { return departmentName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}