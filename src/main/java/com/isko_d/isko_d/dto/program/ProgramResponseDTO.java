package com.isko_d.isko_d.dto.program;

import com.isko_d.isko_d.dto.department.DepartmentResponseDTO;
import com.isko_d.isko_d.model.Program;
import java.time.LocalDateTime;

public class ProgramResponseDTO {
    private Long id;
    private String name;
    private DepartmentResponseDTO department;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProgramResponseDTO(Program program) {
        this.id = program.getId();
        this.name = program.getName();
        this.department = new DepartmentResponseDTO(program.getDepartment());
        this.createdAt = program.getCreatedAt();
        this.updatedAt = program.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public DepartmentResponseDTO getDepartment() { return department; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
