package com.isko_d.isko_d.controller;

import com.isko_d.isko_d.service.ProgramService;
import com.isko_d.isko_d.dto.common.PaginatedResponse;
import com.isko_d.isko_d.dto.program.ProgramRequestDTO;
import com.isko_d.isko_d.dto.program.ProgramResponseDTO;
import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<PaginatedResponse<ProgramResponseDTO>> findAll(
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
            List<ProgramResponseDTO> programs = programService.findAll(searchBy, search, sort);
            return ResponseEntity.ok(new PaginatedResponse<>(programs));
        } else {
            Page<ProgramResponseDTO> programsPage = programService.findPage(page, size, searchBy, search, sort);
            return ResponseEntity.ok(new PaginatedResponse<>(programsPage));
        }
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
