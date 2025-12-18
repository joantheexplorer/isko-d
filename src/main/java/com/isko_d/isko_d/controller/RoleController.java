package com.isko_d.isko_d.controller;

import com.isko_d.isko_d.dto.role.RoleRequestDTO;
import com.isko_d.isko_d.model.Role;
import com.isko_d.isko_d.service.RoleService;
import com.isko_d.isko_d.dto.role.RoleResponseDTO;
import com.isko_d.isko_d.dto.role.RoleResponseDTO;
import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@RestController
@RequestMapping(path="/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> findAll() {
        List<RoleResponseDTO> roles = roleService.findAll();

        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(roles);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<RoleResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> save(
            @RequestBody @Validated(Create.class) RoleRequestDTO request
    ) {
        RoleResponseDTO savedRole = roleService.save(request);
        return ResponseEntity.status(201).body(savedRole);
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<RoleResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(Update.class) RoleRequestDTO request
    ) {
        return ResponseEntity.ok(roleService.update(id, request));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<RoleResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.delete(id));
    }
}