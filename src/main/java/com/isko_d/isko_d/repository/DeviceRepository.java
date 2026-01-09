package com.isko_d.isko_d.repository;

import com.isko_d.isko_d.model.Device;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Page<Device> findByNameContaining(String name, Pageable pageable);
    Page<Device> findByLocationNameContaining(String name, Pageable pageable);

    List<Device> findByNameContaining(String name, Sort sort);
    List<Device> findByLocationNameContaining(String name, Sort sort);
}
