package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.User;
import com.isko_d.isko_d.dto.user.UserRequestDTO;
import com.isko_d.isko_d.dto.user.UserResponseDTO;
import com.isko_d.isko_d.repository.UserRepository;
import com.isko_d.isko_d.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map((user) -> new UserResponseDTO(user))
                .toList();
    }

    public UserResponseDTO findById(Long id) {
        return userRepository.findById(id)
                .map((user) -> new UserResponseDTO(user))
                .orElseThrow(() -> new NotFoundException(User.class, id));
    }

    public UserResponseDTO save(UserRequestDTO request) {
        User saved = userRepository.save(new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword()
        ));

        return new UserResponseDTO(saved);
    }

    public UserResponseDTO update(Long id, UserRequestDTO request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(User.class, id));

        if (request.getFirstName() != null && !request.getFirstName().isBlank()) existing.setFirstName(request.getFirstName());
        if (request.getLastName() != null && !request.getLastName().isBlank()) existing.setLastName(request.getLastName());
        if (request.getEmail() != null && !request.getEmail().isBlank()) existing.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) existing.setPassword(request.getPassword());

        User saved = userRepository.save(existing);

        return new UserResponseDTO(saved);
    }

    public UserResponseDTO delete(Long id) {
        User deleted = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(User.class, id));

        userRepository.deleteById(id);

        return new UserResponseDTO(deleted);
    }
}
