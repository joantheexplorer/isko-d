package com.isko_d.isko_d.dto.location;

import com.isko_d.isko_d.model.Location;
import java.time.LocalDateTime;

public class LocationResponseDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LocationResponseDTO(Location location) {
        this.id = location.getId();
        this.name = location.getName();
        this.createdAt = location.getCreatedAt();
        this.updatedAt = location.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
