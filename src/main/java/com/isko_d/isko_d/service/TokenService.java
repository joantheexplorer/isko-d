package com.isko_d.isko_d.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.isko_d.isko_d.exception.NotFoundException;
import com.isko_d.isko_d.model.Device;
import com.isko_d.isko_d.model.Token;
import com.isko_d.isko_d.model.User;
import com.isko_d.isko_d.repository.TokenRepository;
import com.isko_d.isko_d.security.TokenHasher;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    private final TokenHasher hasher;

    public TokenService(TokenRepository tokenRepository, TokenHasher hasher) {
        this.tokenRepository = tokenRepository;
        this.hasher = hasher;
    }

    public Token findByDevice(Device device) {
        Token token = tokenRepository.findByDevice(device);
        
        if (token == null) {
            throw new NotFoundException(Token.class, device.getId(), "device");
        }

        return token;
    }

    public String createToken(User user) {
        return createToken(user, null);
    }

    public String createToken(Device device) {
        return createToken(null, device);
    }

    private String createToken(User user, Device device) {
        String plain = UUID.randomUUID().toString();
        String lookup = hasher.sha256(plain);
        String hash = hasher.bcrypt(plain);
        LocalDateTime expiresAt = user == null ? null : LocalDateTime.now().plusHours(3);

        Token token = new Token(user, device, lookup, hash, expiresAt);
        tokenRepository.save(token);

        return plain;
    }

    public Token validateToken(String plainToken) {
        String lookup = hasher.sha256(plainToken);
        Token token = tokenRepository.findByTokenLookup(lookup);
        if (token != null 
            && token.getExpiresAt().isAfter(LocalDateTime.now())
            && hasher.matches(plainToken, token.getTokenHash())) {
            return token;
        }
        return null;
    }

    public Token revokeToken(String plainToken) {
        String lookup = hasher.sha256(plainToken);
        Token token = tokenRepository.findByTokenLookup(lookup);
        
        tokenRepository.delete(token);

        return token;
    }
}
