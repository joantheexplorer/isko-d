package com.isko_d.isko_d.repository;

import com.isko_d.isko_d.model.Action;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    Page<Action> findByNameContaining(String name, Pageable pageable);
    List<Action> findByNameContaining(String name, Sort sort);
}
