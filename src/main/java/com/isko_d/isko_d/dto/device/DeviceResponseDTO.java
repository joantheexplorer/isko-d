package com.isko_d.isko_d.dto.device;

import com.isko_d.isko_d.model.Device;
import com.isko_d.isko_d.model.Location;
import java.time.LocalDateTime;

public class DeviceResponseDTO {
    private Long id;
    private Location location_id;
    private String name;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DeviceResponseDTO(Device device){
        this.id=device.getId();
        this.location_id=device.getLocation_id();
        this.name=device.getName();
        this.token=device.getToken();
        this.createdAt=device.getCreatedAt();
        this.updatedAt=device.getUpdatedAt();
    }

    public Long getId() {return id;} 
    public Location getLocation_id()  {return location_id;}
    public String getName() {return name;}
    public String getToken() {return token;}
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    
}


