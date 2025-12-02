package com.isko_d.isko_d.dto.log;

import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import jakarta.validation.constraints.NotBlank;

public class LogRequestDTO {

    @NotBlank(groups=Create.class)
    private String actionType;

    @NotBlank(groups=Create.class)
    private String location;

    @NotBlank(groups=Create.class)
    private String deviceId;

    public String getActionType() { return actionType; }
    public String getLocation() { return location; }
    public String getDeviceId() { return deviceId; }

    public void setActionType(String actionType) { this.actionType = actionType; }
    public void setLocation(String location) { this.location = location; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
}
