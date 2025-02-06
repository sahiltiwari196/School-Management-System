package com.smsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smsytem.securityFilter.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurtiyConfig {
	
	@Autowired
	JwtFilter jwtFilter;
	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
		 http.csrf(csrf -> csrf.disable())
		 .sessionManagement(session -> 
         	session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))            
         .authorizeHttpRequests(auth -> auth             
             .requestMatchers("/login","/h2-console/**").permitAll() 
             .anyRequest().authenticated())         
         .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
         .headers(headers -> headers.frameOptions(frame -> frame.disable()));
		 

     return http.build();
		
		
	}
	
}
