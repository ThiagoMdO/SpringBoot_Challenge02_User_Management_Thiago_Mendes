package com.passuol.sp.challenge03.msuser.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ProjectSecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authorize) -> authorize
                //.requestMatchers(HttpMethod.POST, "/login").permitAll()
                //.requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.PUT,"/v1/users/**").hasRole("USER")
                .requestMatchers("/v1/users", "/v1/login").permitAll()
//                .requestMatchers(HttpMethod.PUT, "/v1/users/{id}").hasRole("USER")
//                .requestMatchers(HttpMethod.PUT, "/v1/users/{id}/password").hasRole("USER")
//                .requestMatchers(HttpMethod.PUT, "v1/users/**").hasRole("USER")
//                .requestMatchers("/v1/users").authenticated()
//            .anyRequest().authenticated()
                .anyRequest().authenticated()

            )
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("v1/users", "v1/login", "/v1/users/**"));

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
