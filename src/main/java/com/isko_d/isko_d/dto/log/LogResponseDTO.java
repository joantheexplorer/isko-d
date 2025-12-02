package com.isko_d.isko_d.dto.log;

import com.isko_d.isko_d.model.Log;
import java.time.LocalDateTime;

public class LogResponseDTO {
    private Long id;
    private String actionType;
    private String location;
    private String deviceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LogResponseDTO(Log log) {
        this.id = log.getId();
        this.actionType = log.getActionType();
        this.location = log.getLocation();
        this.deviceId = log.getDeviceId();
        this.createdAt = log.getCreatedAt();
        this.updatedAt = log.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getActionType() { return actionType; }
    public String getLocation() { return location; }
    public String getDeviceId() { return deviceId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
