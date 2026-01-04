package com.isko_d.isko_d.dto.log;

import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import jakarta.validation.constraints.NotBlank;

public class LogRequestDTO {

    @NotBlank(groups=Create.class)
    private Long deviceId;

    @NotBlank(groups=Create.class)
    private Long actionId;

    public Long getDeviceId() { return deviceId; }
    public Long getActionId() { return actionId; }

    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public void setActionId(Long actionId) { this.actionId = actionId; }
}
