package com.isko_d.isko_d.dto.role;

import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import com.isko_d.isko_d.model.User;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public class RoleRequestDTO {

    @NotBlank(groups=Create.class, message = "name is required")
    private String name;

    private Set<Long> users;

    public String getName() { return name; }
    public Set<Long> getUsers() { return users; }

    public void setName(String name) { this.name = name; }
    public void setUsers(Set<Long> users) { this.users = users; }
}
