package com.isko_d.isko_d.config;

import com.isko_d.isko_d.model.Role;
import com.isko_d.isko_d.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
public class RoleSeeder {

    @Bean
    @Transactional
    @Order(1)
    CommandLineRunner seedRoles(RoleRepository repository) {
        return args -> {
            // Only seed if the table is empty to avoid duplicates
            if (repository.count() == 0) {
                List<Role> roles = List.of(
                    new Role("superadmin"),
                    new Role("admin"),
                    new Role("student")
                );

                repository.saveAll(roles);
                System.out.println("RoleSeeder: Seeded " + roles.size() + " roles.");
            } else {
                System.out.println("RoleSeeder: Roles already exist. Skipping seed.");
            }
        };
    }
}
