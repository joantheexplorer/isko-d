package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Department;
import com.isko_d.isko_d.dto.department.DepartmentRequestDTO;
import com.isko_d.isko_d.dto.department.DepartmentResponseDTO;
import com.isko_d.isko_d.repository.DepartmentRepository;
import com.isko_d.isko_d.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentResponseDTO> findAll() {
        return departmentRepository.findAll()
            .stream()
            .map(DepartmentResponseDTO::new)
            .toList();
    }

    public DepartmentResponseDTO findById(Long id) {
        return departmentRepository.findById(id)
            .map(DepartmentResponseDTO::new)
            .orElseThrow(() -> new NotFoundException(Department.class, id));
    }

    public DepartmentResponseDTO save(DepartmentRequestDTO request) {
        Department saved = departmentRepository.save(new Department(
            request.getName()
        ));

        return new DepartmentResponseDTO(saved);
    }

    public DepartmentResponseDTO update(Long id, DepartmentRequestDTO request) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Department.class, id));

        if (request.getName() != null && !request.getName().isBlank()) {
            existing.setName(request.getName());
        }

        Department saved = departmentRepository.save(existing);
        return new DepartmentResponseDTO(saved);
    }

    public DepartmentResponseDTO delete(Long id) {
        Department deleted = departmentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Department.class, id));

        departmentRepository.deleteById(id);
        return new DepartmentResponseDTO(deleted);
    }
}