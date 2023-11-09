package com.passuol.sp.challenge03.msuser.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authorize) -> authorize
                //.requestMatchers(HttpMethod.POST, "/login").permitAll()
                //.requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers("/v1/users", "/v1/login").permitAll()
                .requestMatchers(HttpMethod.POST, "v1/users/**").hasRole("USER")
                .anyRequest().authenticated()
            );
        httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("v1/users", "v1/login"));

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

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//            .authorizeHttpRequests((requests) -> requests
//            //.requestMatchers("/v1/users").authenticated()
//            .requestMatchers("/v1/users", "/v1/login", "/v1/users/{id}", "/v1/users/{id}/password").permitAll()
//            .anyRequest().authenticated()
//        )
//            .formLogin(Customizer.withDefaults())
//            .httpBasic(Customizer.withDefaults());
//
//        // Configuração para desabilitar a proteção CSRF no endpoint /v1/user
//        http.csrf(csrf -> csrf.ignoringRequestMatchers("/v1/**"));
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//    UserDetailsService userDetailsService,
//    PasswordEncoder passwordEncoder) {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder);
//
//        return new ProviderManager(authenticationProvider);
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//        .username("user")
//        .password("password")
//        .roles("USER")
//        .build();
//
//        return new InMemoryUserDetailsManager(userDetails);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//        .authorizeHttpRequests((requests) -> requests
//            //.requestMatchers("/v1/users").authenticated()
//            .requestMatchers("/v1/users", "/v1/login", "/v1/users/{id}", "/v1/users/{id}/password").permitAll()
//            .anyRequest().authenticated()
//        )
//            .formLogin(Customizer.withDefaults())
//            .httpBasic(Customizer.withDefaults());
//
//        // Configuração para desabilitar a proteção CSRF no endpoint /v1/user
//        http.csrf(csrf -> csrf.ignoringRequestMatchers("/v1/**"));
//
//        return http.build();
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(
//    UserDetailsService userDetailsService,
//    PasswordEncoder passwordEncoder) {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder);
//
//        return new ProviderManager(authenticationProvider);
//    }
//
////    @Bean
////    public InMemoryUserDetailsManager userDetailsManager(){
////
////        UserDetails admin = User.withDefaultPasswordEncoder()
////        .username("admin")
////        .password("12345")
////        .authorities("admin")
////        .build();
////
////    return new InMemoryUserDetailsManager((admin));
////    }
//
////    @Bean
////    public UserDetailsService userDetailsService(DataSource dataSource){
////        return new JdbcUserDetailsManager(dataSource);
////    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//
////    @Bean
////    public PasswordEncoder passwordEncoder(){
////        return NoOpPasswordEncoder.getInstance();
////    }

}
