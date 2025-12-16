package com.isko_d.isko_d.repository;

import com.isko_d.isko_d.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; //added

@Repository

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name); //added for program seeder.

}