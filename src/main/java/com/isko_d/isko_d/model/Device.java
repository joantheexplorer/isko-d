package com.isko_d.isko_d.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@Entity
@Table(name="devices")
@EntityListeners(AuditingEntityListener.class)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id")
    private Location location_id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String token;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Device() {}

    public Device(
        Location location_id,
        String name,
        String token
    ) {
        this.location_id= location_id;
        this.name = name;
        this.token = token;
    }

    public Long getId() { return id; }
    public Location getLocation_id() { return location_id; }
    public String getName() { return name; }
    public String getToken() { return token; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setLocation_id(Location location_id) { this.location_id = location_id; }
    public void setName(String name) { this.name = name; }
    public void setToken(String token) { this.token = token; }

}
