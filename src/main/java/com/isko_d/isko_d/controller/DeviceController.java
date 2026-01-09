package com.isko_d.isko_d.controller;

import com.isko_d.isko_d.model.Device;
import com.isko_d.isko_d.dto.common.PaginatedResponse;
import com.isko_d.isko_d.service.DeviceService;
import com.isko_d.isko_d.dto.device.DeviceRequestDTO;
import com.isko_d.isko_d.dto.device.DeviceResponseDTO;
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
@RequestMapping(path="/devices")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<DeviceResponseDTO>> findAll(
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
            List<DeviceResponseDTO> devices = deviceService.findAll(searchBy, search, sort);
            return ResponseEntity.ok(new PaginatedResponse<>(devices));
        } else {
            Page<DeviceResponseDTO> devicesPage = deviceService.findPage(page, size, searchBy, search, sort);
            return ResponseEntity.ok(new PaginatedResponse<>(devicesPage));
        }
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<DeviceResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DeviceResponseDTO> save(
        @RequestBody @Validated(Create.class) DeviceRequestDTO request
    ) {
        DeviceResponseDTO savedDevice = deviceService.save(request);
        return ResponseEntity.status(201).body(savedDevice);
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<DeviceResponseDTO> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) DeviceRequestDTO request
    ) {
        return ResponseEntity.ok(deviceService.update(id, request));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<DeviceResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.delete(id));
    }
    
}

