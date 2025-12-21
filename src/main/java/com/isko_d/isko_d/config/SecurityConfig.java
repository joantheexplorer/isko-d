package com.isko_d.isko_d.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.isko_d.isko_d.security.BearerTokenAuthFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    BearerTokenAuthFilter bearerTokenAuthFilter;

    public SecurityConfig(BearerTokenAuthFilter bearerTokenAuthFilter) {
        this.bearerTokenAuthFilter = bearerTokenAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(request -> {
                request.requestMatchers("/auth/**").permitAll();
                request.anyRequest().authenticated();
            })
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(bearerTokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
