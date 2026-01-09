package com.isko_d.isko_d.dto.log;

import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LogRequestDTO {

    @NotBlank(groups=Create.class)
    private String barcode;

    @NotNull(groups=Create.class)
    private Long deviceId;

    @NotNull(groups=Create.class)
    private Long actionId;

    public String getBarcode() { return barcode; }
    public Long getDeviceId() { return deviceId; }
    public Long getActionId() { return actionId; }

    public void setBarcode(String barcode) { this.barcode = barcode; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public void setActionId(Long actionId) { this.actionId = actionId; }
}
