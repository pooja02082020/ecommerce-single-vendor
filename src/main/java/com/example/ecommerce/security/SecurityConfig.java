//package com.example.ecommerce.security;
//
//import org.springframework.context.annotation.Bean;
//
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.http.HttpMethod;
//
//import org.springframework.security.authentication.AuthenticationManager;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//import org.springframework.security.config.http.SessionCreationPolicy;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import org.springframework.security.web.SecurityFilterChain;
//
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// 
//@Configuration
//
//@EnableWebSecurity
//
//public class SecurityConfig {
// 
//    private final CustomUserDetailsService userDetailsService;
//
//    private final JwtUtils jwtUtils;
//
//    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtUtils jwtUtils) {
//
//        this.userDetailsService = userDetailsService;
//
//        this.jwtUtils = jwtUtils;
//
//    }
// 
//    @Bean
//
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCrypt) throws Exception {
//
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//
//            .userDetailsService(userDetailsService)
//
//            .passwordEncoder(bCrypt)
//
//            .and()
//
//            .build();
//
//    }
// 
//    @Bean
//
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        JwtFilter jwtFilter = new JwtFilter(jwtUtils, userDetailsService);
//
//        http.csrf().disable()
//
//            .authorizeHttpRequests()
//
//              .requestMatchers("/api/auth/**", "/v3/api-docs/**").permitAll()
//
//              .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()
//
//              .requestMatchers("/api/auth/make-admin/**").permitAll()
//
//              .anyRequest().authenticated()
//
//            .and()
//
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//            .and()
//
//            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
// 
//        return http.build();
//
//    }
// 
//    @Bean
//
//    public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
//
//}
// 
// 