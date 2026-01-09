package com.isko_d.isko_d.repository;

import com.isko_d.isko_d.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByBarcode(String barcode);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    Page<User> findByFirstNameContaining(String firstName, Pageable pageable);
    Page<User> findByMiddleNameContaining(String middleName, Pageable pageable);
    Page<User> findByLastNameContaining(String lastName, Pageable pageable);
    Page<User> findByBarcodeContaining(String barcode, Pageable pageable);
    Page<User> findByEmailContaining(String email, Pageable pageable);
    Page<User> findByRoleNameContaining(String name, Pageable pageable);

    List<User> findByFirstNameContaining(String firstName, Sort sort);
    List<User> findByMiddleNameContaining(String middleName, Sort sort);
    List<User> findByLastNameContaining(String lastName, Sort sort);
    List<User> findByBarcodeContaining(String barcode, Sort sort);
    List<User> findByEmailContaining(String email, Sort sort);
    List<User> findByRoleNameContaining(String name, Sort sort);
}
