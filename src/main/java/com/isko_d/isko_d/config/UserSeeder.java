package com.isko_d.isko_d.config;

import com.isko_d.isko_d.model.User;
import com.isko_d.isko_d.dto.user.UserRequestDTO;
import com.isko_d.isko_d.exception.NotFoundException;
import com.isko_d.isko_d.model.Role;
import com.isko_d.isko_d.repository.UserRepository;
import com.isko_d.isko_d.service.UserService;
import com.isko_d.isko_d.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
public class UserSeeder {
    @Bean
    @Transactional
    @Order(2)
    CommandLineRunner seedUsers(UserRepository userRepository, UserService userService, RoleRepository roleRepository) {
        return args -> {
            // Only seed if the table is empty to avoid duplicates
            if (userRepository.count() == 0) {
                Role superAdminRole = roleRepository.findByName("SUPERADMIN")
                                        .orElseThrow(() -> new NotFoundException(Role.class, "SUPERADMIN", "name"));
                
                UserRequestDTO userInfo = new UserRequestDTO();
                userInfo.setEmail("superadmin@iskod.com");
                userInfo.setFirstName("Super");
                userInfo.setLastName("Admin");
                userInfo.setPassword("qyL2jVlerK8rzu8Ey");
                userInfo.setRoleId(superAdminRole.getId());

                userService.save(userInfo);
            } else {
                System.out.println("UserSeeder: Users already exist. Skipping seed.");
            }
        };
    }
}
