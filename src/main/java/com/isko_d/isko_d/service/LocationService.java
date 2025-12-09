package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Location;
import com.isko_d.isko_d.dto.location.LocationRequestDTO;
import com.isko_d.isko_d.dto.location.LocationResponseDTO;
import com.isko_d.isko_d.repository.LocationRepository;
import com.isko_d.isko_d.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationResponseDTO> findAll() {
        return locationRepository.findAll()
            .stream()
            .map((location) -> new LocationResponseDTO(location))
            .toList();
    }

    public LocationResponseDTO findById(Long id) {
        return locationRepository.findById(id)
            .map((location) -> new LocationResponseDTO(location))
            .orElseThrow(() -> new NotFoundException(Location.class, id));
    }

    public LocationResponseDTO save(LocationRequestDTO request) {
        Location saved = locationRepository.save(new Location(
            request.getName()
        ));

        return new LocationResponseDTO(saved);
    }

    public LocationResponseDTO update(Long id, LocationRequestDTO request) {
        Location existing = locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Location.class, id));

        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());

        Location saved = locationRepository.save(existing);

        return new LocationResponseDTO(saved);
    }

    public LocationResponseDTO delete(Long id) {
        Location deleted = locationRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Location.class, id));

        locationRepository.deleteById(id);

        return new LocationResponseDTO(deleted);
    }
}