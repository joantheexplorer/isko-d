package com.isko_d.isko_d.controller;

import com.isko_d.isko_d.service.DepartmentService;
import com.isko_d.isko_d.dto.department.DepartmentRequestDTO;
import com.isko_d.isko_d.dto.department.DepartmentResponseDTO;
import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@RestController
@RequestMapping(path="/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> findAll() {
        List<DepartmentResponseDTO> departments = departmentService.findAll();

        if (departments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(departments);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<DepartmentResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> save(
        @RequestBody @Validated(Create.class) DepartmentRequestDTO request
    ) {
        DepartmentResponseDTO savedDepartment = departmentService.save(request);
        return ResponseEntity.status(201).body(savedDepartment);
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<DepartmentResponseDTO> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) DepartmentRequestDTO request
    ) {
        return ResponseEntity.ok(departmentService.update(id, request));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<DepartmentResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.delete(id));
    }
}