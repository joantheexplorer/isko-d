package com.isko_d.isko_d.dto.role;

import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import jakarta.validation.constraints.NotBlank;

public class RoleRequestDTO {

    @NotBlank(groups=Create.class, message = "name is required")
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
