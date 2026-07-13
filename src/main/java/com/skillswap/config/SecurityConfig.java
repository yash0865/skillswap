package com.skillswap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disabled for stateless APIs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/rest/users/**").permitAll() 
                .requestMatchers("/rest/skills/**").permitAll()
                .requestMatchers("/rest/session/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
