package com.isko_d.isko_d.dto.log;

import com.isko_d.isko_d.dto.action.ActionResponseDTO;
import com.isko_d.isko_d.dto.device.DeviceResponseDTO;
import com.isko_d.isko_d.dto.user.UserResponseDTO;
import com.isko_d.isko_d.model.Log;
import java.time.LocalDateTime;

public class LogResponseDTO {
    private Long id;
    private UserResponseDTO user;
    private DeviceResponseDTO device;
    private ActionResponseDTO action;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LogResponseDTO(Log log) {
        this.id = log.getId();
        this.user = new UserResponseDTO(log.getUser());
        this.device = new DeviceResponseDTO(log.getDevice());
        this.action = new ActionResponseDTO(log.getAction());
        this.createdAt = log.getCreatedAt();
        this.updatedAt = log.getUpdatedAt();
    }

    public Long getId() { return id; }
    public UserResponseDTO getUser() { return user; }
    public DeviceResponseDTO getDevice() { return device; }
    public ActionResponseDTO getAction() { return action; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
