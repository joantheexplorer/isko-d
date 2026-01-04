package com.isko_d.isko_d.dto.department;

import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import jakarta.validation.constraints.NotBlank;

public class DepartmentRequestDTO {
    @NotBlank(groups=Create.class, message = "Name is required")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

