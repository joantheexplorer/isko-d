package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Device;
import com.isko_d.isko_d.model.Location;
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

    public DeviceService(DeviceRepository deviceRepository, LocationRepository locationRepository){
        this.deviceRepository=deviceRepository;
        this.locationRepository=locationRepository;
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
        Long locationId = request.getLocation_id();
        Location location_id =locationRepository.findById(request.getLocation_id())
            .orElseThrow(() -> new NotFoundException(Location.class, locationId));
        Device saved=deviceRepository.save(new Device(
            location_id,
            request.getName(),
            request.getToken()
        ));

        return new DeviceResponseDTO(saved);
    }

    public DeviceResponseDTO update(Long id, DeviceRequestDTO request){
        Device existing = deviceRepository.findById(id)
             .orElseThrow(() -> new NotFoundException(Device.class, id));

        if (request.getLocation_id() != null){ 
            Long locationId = request.getLocation_id();
            Location location_id =locationRepository.findById(request.getLocation_id())
            .orElseThrow(() -> new NotFoundException(Location.class, locationId));
            existing.setLocation_id(location_id);}
        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());
        if (request.getToken() != null && !request.getToken().isBlank()) existing.setToken(request.getToken());
        
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
