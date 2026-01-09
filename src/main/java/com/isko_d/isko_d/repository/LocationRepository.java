package com.isko_d.isko_d.repository;

import com.isko_d.isko_d.model.Location;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findByNameContaining(String name, Pageable pageable);
    List<Location> findByNameContaining(String name, Sort sort);
}
