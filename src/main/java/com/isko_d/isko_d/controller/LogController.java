package com.isko_d.isko_d.controller;

import com.isko_d.isko_d.model.Log;
import com.isko_d.isko_d.service.LogService;
import com.isko_d.isko_d.dto.common.PaginatedResponse;
import com.isko_d.isko_d.dto.log.LogRequestDTO;
import com.isko_d.isko_d.dto.log.LogResponseDTO;
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
@RequestMapping(path="/logs")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<LogResponseDTO>> findAll(
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
            List<LogResponseDTO> logs = logService.findAll(searchBy, search, sort);
            return ResponseEntity.ok(new PaginatedResponse<>(logs));
        } else {
            Page<LogResponseDTO> logsPage = logService.findPage(page, size, searchBy, search, sort);
            return ResponseEntity.ok(new PaginatedResponse<>(logsPage));
        }
    }

    @PostMapping
    public ResponseEntity<LogResponseDTO> save(
        @RequestBody @Validated(Create.class) LogRequestDTO request
    ) {
        LogResponseDTO savedLog = logService.save(request);
        return ResponseEntity.status(201).body(savedLog);
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<LogResponseDTO> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) LogRequestDTO request
    ) {
        return ResponseEntity.ok(logService.update(id, request));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<LogResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(logService.delete(id));
    }
}
