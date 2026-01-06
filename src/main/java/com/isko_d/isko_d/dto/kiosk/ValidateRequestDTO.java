package com.isko_d.isko_d.dto.kiosk;

import jakarta.validation.constraints.NotBlank;

public class ValidateRequestDTO {
    @NotBlank
    String plainToken;

    public String getPlainToken() { return plainToken; }

    public void setPlainToken(String plainToken) { this.plainToken = plainToken; }
}
