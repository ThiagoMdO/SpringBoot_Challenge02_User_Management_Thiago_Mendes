package com.passuol.sp.challenge03.msuser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((requests) -> requests
        .requestMatchers("/users").authenticated()
        .requestMatchers("/v1/users", "/v1/login", "/v1/users/{id}", "/v1/users/{id}/password").permitAll())
        .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults());

        // Configuração para desabilitar a proteção CSRF no endpoint /v1/user
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/v1/users", "/v1/login","/v1/users/{id}","/v1/users/{id}/password"));

        return http.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//        .username("admin")
//        .password("12345")
//        .authorities("admin")
//        .build();
//
//    return new InMemoryUserDetailsManager((admin));
//    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
