package com.isko_d.isko_d.controller;

import com.isko_d.isko_d.dto.role.RoleRequestDTO;
import com.isko_d.isko_d.dto.common.PaginatedResponse;
import com.isko_d.isko_d.model.Role;
import com.isko_d.isko_d.service.RoleService;
import com.isko_d.isko_d.dto.role.RoleResponseDTO;
import com.isko_d.isko_d.dto.role.RoleResponseDTO;
import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<PaginatedResponse<RoleResponseDTO>> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size,
        @RequestParam(required = false) String searchBy,
        @RequestParam(required = false) String search,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir,
        @RequestParam(defaultValue = "false") boolean all
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
        
        if (all) {
            List<RoleResponseDTO> roles = roleService.findAll(searchBy, search, sort);
            return ResponseEntity.ok(new PaginatedResponse<>(roles));
        } else {
            Page<RoleResponseDTO> rolesPage = roleService.findPage(page, size, searchBy, search, sort);
            return ResponseEntity.ok(new PaginatedResponse<>(rolesPage));
        }
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
