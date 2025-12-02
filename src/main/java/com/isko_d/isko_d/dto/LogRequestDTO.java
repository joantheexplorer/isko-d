package com.isko_d.isko_d.dto;

public class LogRequestDTO {
    private String actionType;
    private String location;
    private String deviceId;

    public String getActionType() { return actionType; }
    public String getLocation() { return location; }
    public String getDeviceId() { return deviceId; }

    public void setActionType(String actionType) { this.actionType = actionType; }
    public void setLocation(String location) { this.location = location; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
}
