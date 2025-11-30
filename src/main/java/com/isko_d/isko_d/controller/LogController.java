package com.isko_d.isko_d.controller;

import com.isko_d.isko_d.model.Log;
import com.isko_d.isko_d.service.LogService;
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
    public ResponseEntity<List<Log>> getAllLogs() {
        List<Log> logs = logService.findAll();

        if (logs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(logs);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Log> getLog(@PathVariable Integer id) {
        return ResponseEntity.ok(logService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Log> createLog(@RequestBody @Valid Log log) {
        Log savedLog = logService.save(log);
        return ResponseEntity.status(201).body(savedLog);
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<Log> updateLog(@PathVariable Integer id, @RequestBody @Valid Log log) {
        return ResponseEntity.ok(logService.update(id, log));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Log> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(logService.delete(id));
    }
}
