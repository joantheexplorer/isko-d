package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Role;
import com.isko_d.isko_d.model.User;
import com.isko_d.isko_d.dto.login.LoginResponseDTO;
import com.isko_d.isko_d.dto.user.StudentRequestDTO;
import com.isko_d.isko_d.dto.user.UserRequestDTO;
import com.isko_d.isko_d.dto.user.UserResponseDTO;
import com.isko_d.isko_d.repository.UserRepository;
import com.isko_d.isko_d.repository.RoleRepository;
import com.isko_d.isko_d.exception.NotFoundException;
import com.isko_d.isko_d.exception.UnauthorizedException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(10);

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenService = tokenService;
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

    public UserResponseDTO findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map((user) -> new UserResponseDTO(user))
                .orElseThrow(() -> new NotFoundException(User.class, email, "email"));
    }

    public UserResponseDTO save(UserRequestDTO request) {
        Role role = roleRepository.findById(request.getRoleId())
            .orElseThrow(() -> new NotFoundException(Role.class, request.getRoleId()));

        User saved = new User(
                request.getBarcode(),
                request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getEmail(),
                bCryptEncoder.encode(request.getPassword()),
                role
        );

        return new UserResponseDTO(userRepository.save(saved));
    }

    public UserResponseDTO update(Long id, UserRequestDTO request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(User.class, id));

        if (request.getFirstName() != null && !request.getFirstName().isBlank()) existing.setFirstName(request.getFirstName());
        if (request.getMiddleName() != null && !request.getMiddleName().isBlank()) existing.setMiddleName(request.getMiddleName());
        if (request.getLastName() != null && !request.getLastName().isBlank()) existing.setLastName(request.getLastName());
        if (request.getEmail() != null && !request.getEmail().isBlank()) existing.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) existing.setPassword(request.getPassword());
        if (request.getRoleId() != null){ 
            Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new NotFoundException(Role.class, request.getRoleId()));
            existing.setRole(role);
        }

        User saved = userRepository.save(existing);

        return new UserResponseDTO(saved);
    }

    public UserResponseDTO delete(Long id) {
        User deleted = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(User.class, id));

        userRepository.deleteById(id);

        return new UserResponseDTO(deleted);
    }

    public LoginResponseDTO authenticateAdmin(String email, String password) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("Incorrect credentials"));

        boolean isAdmin = user.getRole().getName().equals("ADMIN")
            || user.getRole().getName().equals("SUPERADMIN");

        if (isAdmin && bCryptEncoder.matches(password, user.getPassword())) {
            return new LoginResponseDTO(user, tokenService.createToken(user));
        } else {
            throw new UnauthorizedException("Incorrect credentials");
        }
    }

    public UserResponseDTO registerStudent(StudentRequestDTO request) {
        Role studentRole = roleRepository.findByName("STUDENT")
            .orElseThrow(() -> new NotFoundException(Role.class, "STUDENT", "name"));
        
        User saved = new User(
                request.getBarcode(),
                request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getEmail(),
                bCryptEncoder.encode(request.getPassword()),
                studentRole
        );


        saved.setRole(studentRole);

        return new UserResponseDTO(userRepository.save(saved));
    }
}
