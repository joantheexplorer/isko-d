package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Role;
import com.isko_d.isko_d.dto.role.RoleRequestDTO;
import com.isko_d.isko_d.dto.role.RoleResponseDTO;
import com.isko_d.isko_d.repository.RoleRepository;
import com.isko_d.isko_d.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleResponseDTO> findAll() {
        return roleRepository.findAll()
                .stream()
                .map((role) -> new RoleResponseDTO(role))
                .toList();
    }

    public RoleResponseDTO findById(Long id) {
        return roleRepository.findById(id)
                .map((role) -> new RoleResponseDTO(role))
                .orElseThrow(() -> new NotFoundException(Role.class, id));
    }

    public RoleResponseDTO save(RoleRequestDTO request) {
        Role saved = roleRepository.save(new Role(
                request.getName()
        ));

        return new RoleResponseDTO(saved);
    }

    public RoleResponseDTO update(Long id, RoleRequestDTO request) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Role.class, id));

        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());
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