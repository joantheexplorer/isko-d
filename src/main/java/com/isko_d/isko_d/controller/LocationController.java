package com.isko_d.isko_d.controller;

import com.isko_d.isko_d.model.Location;
import com.isko_d.isko_d.service.LocationService;
import com.isko_d.isko_d.dto.location.LocationRequestDTO;
import com.isko_d.isko_d.dto.location.LocationResponseDTO;
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
@RequestMapping(path="/locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<LocationResponseDTO>> findAll() {
        List<LocationResponseDTO> locations = locationService.findAll();

        if (locations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(locations);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<LocationResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<LocationResponseDTO> save(
        @RequestBody @Validated(Create.class) LocationRequestDTO request
    ) {
        LocationResponseDTO savedLocation = locationService.save(request);
        return ResponseEntity.status(201).body(savedLocation);
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<LocationResponseDTO> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) LocationRequestDTO request
    ) {
        return ResponseEntity.ok(locationService.update(id, request));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<LocationResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.delete(id));
    }
}
