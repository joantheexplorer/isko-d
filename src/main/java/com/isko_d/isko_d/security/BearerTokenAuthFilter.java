package com.isko_d.isko_d.security;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.isko_d.isko_d.exception.UnauthorizedException;
import com.isko_d.isko_d.model.Token;
import com.isko_d.isko_d.model.User;
import com.isko_d.isko_d.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class BearerTokenAuthFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    public BearerTokenAuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        Token token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String plainToken = authHeader.substring(7);
            token = tokenService.validateToken(plainToken);
        } else if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals("api_token")) {
                    String plainToken = c.getValue();
                    token = tokenService.validateToken(plainToken);
                }
            }
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        if (token != null) {
            Object principal;
            GrantedAuthority authority;

            if (token.getUser() != null) {
                principal = token.getUser();
                authority = new SimpleGrantedAuthority("ROLE_" + token.getUser().getRole().getName());
            } else {
                principal = token.getDevice();
                authority = new SimpleGrantedAuthority("ROLE_DEVICE");
            }

            Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, List.of(authority));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            throw new UnauthorizedException("Invalid or expired API token");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        return path.equals("/api/auth/login")
            || path.equals("/api/auth/register")
            || path.equals("/api/auth/logout");
    }
}
