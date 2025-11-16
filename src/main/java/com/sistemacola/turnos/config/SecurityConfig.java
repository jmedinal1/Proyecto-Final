package com.sistemacola.turnos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login", "/login.html",
                                "/css/**", "/js/**", "/images/**", "/webjars/**",
                                "/index.html", "/admin.html", "/recepcion.html"
                        ).permitAll()
                        .anyRequest().permitAll()   // para tu proyecto final está bien
                );

        // MUY IMPORTANTE: quitar el login por defecto de Spring
        http.formLogin(form -> form.disable());

        return http.build();
    }
}

