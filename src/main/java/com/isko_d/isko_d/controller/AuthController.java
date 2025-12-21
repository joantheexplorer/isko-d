package com.isko_d.isko_d.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isko_d.isko_d.dto.user.UserRequestDTO;
import com.isko_d.isko_d.dto.user.UserResponseDTO;
import com.isko_d.isko_d.model.User;
import com.isko_d.isko_d.service.TokenService;
import com.isko_d.isko_d.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(10);

    public AuthController(
            UserService userService,
            TokenService tokenService
    ) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserRequestDTO request) {
        String plainToken = userService.authenticate(request.getEmail(), request.getPassword());
        if (plainToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
        } else {
            return ResponseEntity.ok(plainToken);
        }
    }
}
