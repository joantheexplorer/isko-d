package com.isko_d.isko_d.repository;

import com.isko_d.isko_d.model.Program;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProgramRepository extends JpaRepository<Program, Long> {
    Page<Program> findByNameContaining(String name, Pageable pageable);
    Page<Program> findByDepartmentNameContaining(String name, Pageable pageable);

    List<Program> findByNameContaining(String name, Sort sort);
    List<Program> findByDepartmentNameContaining(String name, Sort sort);
}
