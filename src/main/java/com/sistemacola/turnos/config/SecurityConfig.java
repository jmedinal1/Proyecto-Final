package com.sistemacola.turnos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //Desactivar CSRF para poder hacer POST desde los HTML
                .csrf(csrf -> csrf.disable())
                // Permitir TODO sin autenticación
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        //Desactivar completamente el login por defecto de Spring Security
        http.formLogin(form -> form.disable());
        http.httpBasic(basic -> basic.disable());

        return http.build();
    }
}

