package com.isko_d.isko_d.dto.program;

import com.isko_d.isko_d.validation.Create;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProgramRequestDTO {

    @NotBlank(groups=Create.class, message = "Program name is required")
    private String name;

    @NotNull(groups=Create.class, message = "Department ID is required")
    private Long departmentId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
}