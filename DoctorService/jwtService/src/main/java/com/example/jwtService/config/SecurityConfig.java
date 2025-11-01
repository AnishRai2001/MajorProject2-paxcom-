package com.example.jwtService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.jwtService.Service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService userdetailsService;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

@Bean
public AuthenticationProvider authprovider() {
	DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
	provider.setUserDetailsService(userdetailsService);
	provider.setPasswordEncoder(passwordEncoder());
	return provider;
}

@Bean
public PasswordEncoder passwordEncoder() {
	return new  BCryptPasswordEncoder();
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	return  http.csrf(csrf->csrf.disable())
			.authorizeHttpRequests(req ->req
				//	.requestMatchers(null)
					.anyRequest().permitAll())
	.build();
}
}
