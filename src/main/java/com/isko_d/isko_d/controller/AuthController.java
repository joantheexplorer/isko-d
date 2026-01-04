package com.isko_d.isko_d.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isko_d.isko_d.dto.login.LoginRequestDTO;
import com.isko_d.isko_d.dto.login.LoginResponseDTO;
import com.isko_d.isko_d.dto.user.StudentRequestDTO;
import com.isko_d.isko_d.dto.user.UserRequestDTO;
import com.isko_d.isko_d.dto.user.UserResponseDTO;
import com.isko_d.isko_d.service.TokenService;
import com.isko_d.isko_d.service.UserService;
import com.isko_d.isko_d.validation.Create;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final TokenService tokenService;

    public AuthController(
            UserService userService,
            TokenService tokenService
    ) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticateUser(@RequestBody @Validated(Create.class) LoginRequestDTO request) {
        LoginResponseDTO authPayload = userService.authenticateAdmin(request.getEmail(), request.getPassword());
        if (authPayload == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            ResponseCookie cookie = ResponseCookie.from("api_token", authPayload.getPlainToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(60 * 60 * 3)
                .build();

            return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authPayload);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerStudent(@RequestBody @Validated(Create.class) StudentRequestDTO request) {
        UserResponseDTO student = userService.registerStudent(request);
        return ResponseEntity.status(201).body(student);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> revokeToken(HttpServletRequest request, HttpServletResponse response) {
        String plainToken = null;

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            plainToken = authHeader.substring(7);
        }

        if (plainToken == null && request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals("api_token")) {
                    plainToken = c.getValue();
                    break;
                }
            }
        }

        if (plainToken != null) {
            tokenService.revokeToken(plainToken);
        }

        ResponseCookie cookie = ResponseCookie.from("api_token", "")
            .httpOnly(true)
            .secure(true)
            .sameSite("Lax")
            .path("/")
            .maxAge(0)
            .build();

        return ResponseEntity.noContent()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .build();
    }
}
