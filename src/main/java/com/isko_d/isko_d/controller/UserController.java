package com.isko_d.isko_d.controller;

import com.isko_d.isko_d.model.User;
import com.isko_d.isko_d.service.UserService;
import com.isko_d.isko_d.dto.user.UserResponseDTO;
import com.isko_d.isko_d.dto.user.UserRequestDTO;
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
@PreAuthorize("hasRole('SUPERADMIN')")
@RequestMapping(path="/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> users = userService.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> save(
            @RequestBody @Validated(Create.class) UserRequestDTO request
    ) {
        UserResponseDTO savedUser = userService.save(request);
        return ResponseEntity.status(201).body(savedUser);
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(Update.class) UserRequestDTO request
    ) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<UserResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }
}
