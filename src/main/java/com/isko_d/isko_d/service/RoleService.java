package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Role;
import com.isko_d.isko_d.model.User;
import com.isko_d.isko_d.dto.role.RoleRequestDTO;
import com.isko_d.isko_d.dto.role.RoleResponseDTO;
import com.isko_d.isko_d.repository.RoleRepository;
import com.isko_d.isko_d.repository.UserRepository;
import com.isko_d.isko_d.exception.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<RoleResponseDTO> findAll(
        String searchBy,
        String search,
        Sort sort
    ) {
        if (searchBy != null && search != null) {
            switch (searchBy.toLowerCase()) {
                case "name":
                    return roleRepository
                        .findByNameContaining(search, sort)
                        .stream()
                        .map(RoleResponseDTO::new)
                        .toList();
            }
        }

        return roleRepository
            .findAll(sort)
            .stream()
            .map(RoleResponseDTO::new)
            .toList();
    }

    public Page<RoleResponseDTO> findPage(
        int page,
        int size,
        String searchBy,
        String search,
        Sort sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if (searchBy != null && search != null) {
            switch (searchBy.toLowerCase()) {
                case "name":
                    return roleRepository
                        .findByNameContaining(search, pageRequest)
                        .map(RoleResponseDTO::new);
            }
        }

        return roleRepository
            .findAll(pageRequest)
            .map(RoleResponseDTO::new);
    }

    public RoleResponseDTO findById(Long id) {
        return roleRepository.findById(id)
                .map((role) -> new RoleResponseDTO(role))
                .orElseThrow(() -> new NotFoundException(Role.class, id));
    }

    public RoleResponseDTO save(RoleRequestDTO request) {
        Role saved = new Role();

        saved.setName(request.getName());

        request.getUsers().forEach(userId -> {
            userRepository.findById(userId).ifPresent(user -> saved.getUsers().add(user));
        });

        return new RoleResponseDTO(roleRepository.save(saved));
    }

    public RoleResponseDTO update(Long id, RoleRequestDTO request) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Role.class, id));

        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());

        request.getUsers().forEach(userId -> {
            userRepository.findById(userId).ifPresent(user -> existing.getUsers().add(user));
        });

        Role saved = roleRepository.save(existing);

        return new RoleResponseDTO(saved);
    }

    public RoleResponseDTO delete(Long id) {
        Role deleted = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Role.class, id));

        roleRepository.deleteById(id);

        return new RoleResponseDTO(deleted);
    }
}
