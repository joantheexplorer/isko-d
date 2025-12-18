package com.isko_d.isko_d.dto.device;

import com.isko_d.isko_d.model.Location;
import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DeviceRequestDTO {
    @NotNull(groups = {Create.class})
    private Long location_id;

    @NotBlank(groups = {Create.class})
    private String name;

    @NotBlank(groups = {Create.class})
    private String token;

    public Long getLocation_id()  {return location_id;}
    public String getName() {return name;}
    public String getToken() {return token;}

    public void setLocation_id(Long location_id) { this.location_id = location_id; }
    public void setName(String name) { this.name =name; }
    public void setToken(String token) { this.token =token; }
}
