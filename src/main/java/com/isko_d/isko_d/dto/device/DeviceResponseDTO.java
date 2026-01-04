package com.isko_d.isko_d.dto.device;

import com.isko_d.isko_d.dto.location.LocationResponseDTO;
import com.isko_d.isko_d.model.Device;
import java.time.LocalDateTime;

public class DeviceResponseDTO {
    private Long id;
    private LocationResponseDTO location;
    private String name;
    private String plainToken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DeviceResponseDTO(Device device){
        this.id = device.getId();
        this.location = new LocationResponseDTO(device.getLocation());
        this.name = device.getName();
        this.createdAt = device.getCreatedAt();
        this.updatedAt = device.getUpdatedAt();
    }

    public DeviceResponseDTO(Device device, String plainToken){
        this.id = device.getId();
        this.location = new LocationResponseDTO(device.getLocation());
        this.name = device.getName();
        this.createdAt = device.getCreatedAt();
        this.updatedAt = device.getUpdatedAt();

        this.plainToken = plainToken;
    }

    public Long getId() { return id; } 
    public LocationResponseDTO getLocation() { return location; }
    public String getName() { return name; }
    public String getPlainToken() { return plainToken; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}


