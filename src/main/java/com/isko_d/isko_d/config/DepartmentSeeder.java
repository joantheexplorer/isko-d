package com.isko_d.isko_d.config;

import com.isko_d.isko_d.model.Department;
import com.isko_d.isko_d.repository.DepartmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DepartmentSeeder {

    @Bean
    CommandLineRunner seedDepartments(DepartmentRepository repository) {
        return args -> {
            // Only seed if the table is empty to avoid duplicates
            if (repository.count() == 0) {
                List<Department> departments = List.of(
                    new Department("College of Accountancy and Finance (CAF)"),
                    new Department("College of Architecture, Design and the Built Environment (CADBE)"),
                    new Department("College of Arts and Letters (CAL)"),
                    new Department("College of Business Administration (CBA)"),
                    new Department("College of Communication (COC)"),
                    new Department("College of Computer and Information Sciences (CCIS)"),
                    new Department("College of Education (COED)"),
                    new Department("College of Engineering (CE)"),
                    new Department("College of Human Kinetics (CHK)"),
                    new Department("College of Law (CL)"),
                    new Department("College of Political Science and Public Administration (CPSPA)"),
                    new Department("College of Social Sciences and Development (CSSD)"),
                    new Department("College of Science (CS)"),
                    new Department("College of Tourism, Hospitality and Transportation Management (CTHTM)"),
                    new Department("Institute of Technology")
                );

                repository.saveAll(departments);
                System.out.println("DepartmentSeeder: Seeded " + departments.size() + " departments.");
            } else {
                System.out.println("DepartmentSeeder: Departments already exist. Skipping seed.");
            }
        };
    }
}