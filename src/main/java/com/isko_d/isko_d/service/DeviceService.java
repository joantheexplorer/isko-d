package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Device;
import com.isko_d.isko_d.model.Location;
import com.isko_d.isko_d.model.Token;
import com.isko_d.isko_d.dto.device.DeviceRequestDTO;
import com.isko_d.isko_d.dto.device.DeviceResponseDTO;
import com.isko_d.isko_d.exception.NotFoundException;
import com.isko_d.isko_d.repository.DeviceRepository;
import com.isko_d.isko_d.repository.LocationRepository;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final LocationRepository locationRepository;
    private final TokenService tokenService;

    public DeviceService(
        DeviceRepository deviceRepository,
        LocationRepository locationRepository,
        TokenService tokenService
    ){
        this.deviceRepository=deviceRepository;
        this.locationRepository=locationRepository;
        this.tokenService = tokenService;
    }

    public List<DeviceResponseDTO> findAll(){
        return deviceRepository.findAll()
        .stream()
        .map((device)->new DeviceResponseDTO(device))
        .toList();
    }

    public DeviceResponseDTO findById(Long id){
        return deviceRepository.findById(id)
        .map((device)->new DeviceResponseDTO(device))
        .orElseThrow(() -> new NotFoundException(Device.class, id));
    }

    public DeviceResponseDTO save(DeviceRequestDTO request){
        Location location = locationRepository.findById(request.getLocationId())
            .orElseThrow(() -> new NotFoundException(Location.class, request.getLocationId()));

        Device saved = deviceRepository.save(new Device(
            location,
            request.getName(),
            null
        ));

        String plainToken = tokenService.createToken(saved);

        Token token = tokenService.findByDevice(saved);
        saved.setToken(token);

        deviceRepository.save(saved);

        return new DeviceResponseDTO(saved, plainToken);
    }

    public DeviceResponseDTO update(Long id, DeviceRequestDTO request){
        Device existing = deviceRepository.findById(id)
             .orElseThrow(() -> new NotFoundException(Device.class, id));

        if (request.getLocationId() != null){ 
            Location location =locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new NotFoundException(Location.class, request.getLocationId()));
            existing.setLocation(location);
        }
        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());
        
        Device saved = deviceRepository.save(existing);
        return new DeviceResponseDTO(saved);
    }

    public DeviceResponseDTO delete(Long id) {
        Device device = deviceRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Device.class, id));

        deviceRepository.deleteById(id);

        return new DeviceResponseDTO(device);
    }
    
}
