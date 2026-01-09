package com.isko_d.isko_d.controller;

import com.isko_d.isko_d.model.Log;
import com.isko_d.isko_d.model.Token;
import com.isko_d.isko_d.service.DeviceService;
import com.isko_d.isko_d.service.LogService;
import com.isko_d.isko_d.service.TokenService;
import com.isko_d.isko_d.dto.device.DeviceResponseDTO;
import com.isko_d.isko_d.dto.kiosk.ValidateRequestDTO;
import com.isko_d.isko_d.dto.log.LogRequestDTO;
import com.isko_d.isko_d.dto.log.LogResponseDTO;
import com.isko_d.isko_d.validation.Create;
import com.isko_d.isko_d.validation.Update;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@RestController
@RequestMapping(path="/kiosk")
public class KioskController {
    private final LogService logService;
    private final TokenService tokenService;

    public KioskController(
        LogService logService,
        TokenService tokenService
    ) {
        this.logService = logService;
        this.tokenService = tokenService;
    }

    @PostMapping(path="/validate")
    public ResponseEntity<DeviceResponseDTO> validateToken(
        @RequestBody @Validated ValidateRequestDTO request
    ) {
        Token token = tokenService.validateToken(request.getPlainToken());
        return ResponseEntity.ok(new DeviceResponseDTO(token.getDevice()));
    } 
    
    @PostMapping(path="/log")
    public ResponseEntity<LogResponseDTO> logEvent(
        @RequestBody @Validated(Create.class) LogRequestDTO request
    ) {
        LogResponseDTO savedLog = logService.save(request);
        return ResponseEntity.status(201).body(savedLog);
    }
}
