package com.isko_d.isko_d.dto.device;

import com.isko_d.isko_d.model.Location;
import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DeviceRequestDTO {
    @NotNull(groups = {Create.class})
    private Long locationId;

    @NotBlank(groups = {Create.class})
    private String name;

    public Long getLocationId() { return locationId; }
    public String getName() { return name; }

    public void setLocationId(Long locationId) { this.locationId = locationId; }
    public void setName(String name) { this.name =name; }
}
