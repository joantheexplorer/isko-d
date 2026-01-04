package com.isko_d.isko_d.config;

import java.time.Instant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.isko_d.isko_d.exception.ForbiddenException;
import com.isko_d.isko_d.exception.UnauthorizedException;
import com.isko_d.isko_d.security.BearerTokenAuthFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    private final BearerTokenAuthFilter bearerTokenAuthFilter;

    public SecurityConfig(
            BearerTokenAuthFilter bearerTokenAuthFilter
    ) {
        this.bearerTokenAuthFilter = bearerTokenAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {})
            .authorizeHttpRequests(request -> {
                request.requestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/logout").permitAll();
                request.anyRequest().authenticated();
            })
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(bearerTokenAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint((request, response, exception) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.getWriter().write(
                                "{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"" + exception.getMessage() + "\",\"path\":\""
                                + request.getRequestURI() + "\"," + "\"timestamp\":\"" + Instant.now() + "\"}"
                        );
                    })
                    .accessDeniedHandler((request, response, exception) -> {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setContentType("application/json");
                        response.getWriter().write(
                                "{\"status\":403,\"error\":\"Forbidden\",\"message\":\"" + exception.getMessage() + "\",\"path\":\""
                                + request.getRequestURI() + "\"," + "\"timestamp\":\"" + Instant.now() + "\"}"
                        );
                    })
                    );

            return http.build();
    }
}
