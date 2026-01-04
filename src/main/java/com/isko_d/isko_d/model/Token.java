package com.isko_d.isko_d.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.ValidationException;

@Entity
@Table(name="tokens")
@EntityListeners(AuditingEntityListener.class)
public class Token {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(nullable = false, unique = true)
    private String tokenLookup; // SHA256

    @Column(nullable = false)
    private String tokenHash; // BCrypt

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime expiresAt;

    public Token() {}

    public Token(
        User user,
        Device device,
        String tokenLookup,
        String tokenHash,
        LocalDateTime expiresAt
    ) {
        this.user = user;
        this.device = device;
        this.tokenLookup = tokenLookup;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Device getDevice() { return device; }
    public String getTokenLookup() { return tokenLookup; }
    public String getTokenHash() { return tokenHash; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }

    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setDevice(Device device) { this.device = device; }
    public void setTokenLookup(String tokenLookup) { this.tokenLookup = tokenLookup; }
    public void setTokenHash(String tokenHash) { this.tokenHash = tokenHash; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    @PrePersist
    @PreUpdate
    private void validateOwner() {
        if ((user == null && device == null) || (user != null && device != null)) {
            throw new ValidationException("Only either user or device must be set");
        }
    }
}
