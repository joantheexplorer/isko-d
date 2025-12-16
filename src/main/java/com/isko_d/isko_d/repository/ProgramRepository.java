package com.isko_d.isko_d.repository;

import com.isko_d.isko_d.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProgramRepository extends JpaRepository<Program, Long> {
 
}