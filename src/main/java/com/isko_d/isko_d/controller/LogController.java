package com.isko_d.isko_d.controller;

import com.isko_d.isko_d.model.Log;
import com.isko_d.isko_d.service.LogService;
import com.isko_d.isko_d.dto.LogRequestDTO;
import com.isko_d.isko_d.dto.LogResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path="/logs")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public ResponseEntity<List<LogResponseDTO>> getAllLogs() {
        List<LogResponseDTO> logs = logService.findAll();

        if (logs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(logs);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<LogResponseDTO> getLog(@PathVariable Long id) {
        return ResponseEntity.ok(logService.findById(id));
    }

    @PostMapping
    public ResponseEntity<LogResponseDTO> createLog(@RequestBody @Valid LogRequestDTO request) {
        LogResponseDTO savedLog = logService.save(request);
        return ResponseEntity.status(201).body(savedLog);
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<LogResponseDTO> updateLog(@PathVariable Long id, @RequestBody LogRequestDTO request) {
        return ResponseEntity.ok(logService.update(id, request));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<LogResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(logService.delete(id));
    }
}
