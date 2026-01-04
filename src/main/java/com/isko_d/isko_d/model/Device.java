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
    private Location location;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(
        mappedBy = "device",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Token token;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Device() {}

    public Device(
        Location location,
        String name,
        Token token
    ) {
        this.location= location;
        this.name = name;
        this.token = token;
    }

    public Long getId() { return id; }
    public Location getLocation() { return location; }
    public String getName() { return name; }
    public Token getToken() { return token; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setLocation(Location location) { this.location = location; }
    public void setName(String name) { this.name = name; }
    public void setToken(Token token) { this.token = token; }
}
