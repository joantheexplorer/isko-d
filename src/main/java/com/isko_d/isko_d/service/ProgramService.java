package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Program;
import com.isko_d.isko_d.model.Department;
import com.isko_d.isko_d.dto.program.ProgramRequestDTO;
import com.isko_d.isko_d.dto.program.ProgramResponseDTO;
import com.isko_d.isko_d.repository.ProgramRepository;
import com.isko_d.isko_d.repository.DepartmentRepository;
import com.isko_d.isko_d.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProgramService {
    private final ProgramRepository programRepository;
    private final DepartmentRepository departmentRepository;

    public ProgramService(ProgramRepository programRepository, DepartmentRepository departmentRepository) {
        this.programRepository = programRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<ProgramResponseDTO> findAll() {
        return programRepository.findAll()
            .stream()
            .map(ProgramResponseDTO::new)
            .toList();
    }

    public ProgramResponseDTO findById(Long id) {
        return programRepository.findById(id)
            .map(ProgramResponseDTO::new)
            .orElseThrow(() -> new NotFoundException(Program.class, id));
    }

    public ProgramResponseDTO save(ProgramRequestDTO request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
            .orElseThrow(() -> new NotFoundException(Department.class, request.getDepartmentId()));

        Program saved = programRepository.save(new Program(
            request.getName(),
            department
        ));

        return new ProgramResponseDTO(saved);
    }

    public ProgramResponseDTO update(Long id, ProgramRequestDTO request) {
        Program existing = programRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Program.class, id));

        if (request.getName() != null && !request.getName().isBlank()) {
            existing.setName(request.getName());
        }

        if (request.getDepartmentId() != null) {
             Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new NotFoundException(Department.class, request.getDepartmentId()));
             existing.setDepartment(department);
        }

        Program saved = programRepository.save(existing);
        return new ProgramResponseDTO(saved);
    }

    public ProgramResponseDTO delete(Long id) {
        Program deleted = programRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Program.class, id));

        programRepository.deleteById(id);
        return new ProgramResponseDTO(deleted);
    }
}