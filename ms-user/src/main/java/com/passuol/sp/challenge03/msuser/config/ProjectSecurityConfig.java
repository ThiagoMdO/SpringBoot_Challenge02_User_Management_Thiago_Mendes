package com.passuol.sp.challenge03.msuser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((requests) -> requests
        .requestMatchers("/users").authenticated()
        .requestMatchers("/v1/users", "/v1/login").permitAll())
        .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults());

        // Configuração para desabilitar a proteção CSRF no endpoint /v1/user
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/v1/users", "/v1/login"));

        return http.build();
    }
}
