package com.isko_d.isko_d.repository;

import com.isko_d.isko_d.model.Log;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    Page<Log> findByUserBarcodeContaining(String userBarcode, Pageable pageable);
    List<Log> findByUserBarcodeContaining(String userBarcode, Sort sort);

    Page<Log> findByDeviceLocationNameContaining(String name, Pageable pageable);
    List<Log> findByDeviceLocationNameContaining(String name, Sort sort);

    Page<Log> findByActionNameContaining(String name, Pageable pageable);
    List<Log> findByActionNameContaining(String name, Sort sort);

    Page<Log> findByDeviceNameContaining(String name, Pageable pageable);
    List<Log> findByDeviceNameContaining(String name, Sort sort);
}
