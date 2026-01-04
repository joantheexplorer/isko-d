package com.isko_d.isko_d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isko_d.isko_d.model.Device;
import com.isko_d.isko_d.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByDevice(Device device);
    Token findByTokenLookup(String tokenLookup);
}
