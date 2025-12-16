package com.isko_d.isko_d.controller;

import com.isko_d.isko_d.service.ProgramService;
import com.isko_d.isko_d.dto.program.ProgramRequestDTO;
import com.isko_d.isko_d.dto.program.ProgramResponseDTO;
import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@RestController
@RequestMapping(path="/programs")
public class ProgramController {
    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping
    public ResponseEntity<List<ProgramResponseDTO>> findAll() {
        List<ProgramResponseDTO> programs = programService.findAll();
        if (programs.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(programs);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<ProgramResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(programService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProgramResponseDTO> save(
        @RequestBody @Validated(Create.class) ProgramRequestDTO request
    ) {
        return ResponseEntity.status(201).body(programService.save(request));
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<ProgramResponseDTO> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) ProgramRequestDTO request
    ) {
        return ResponseEntity.ok(programService.update(id, request));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<ProgramResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(programService.delete(id));
    }
}