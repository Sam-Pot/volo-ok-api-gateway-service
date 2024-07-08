package com.volook.apiGateway.auth;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class JwtPolicy {
	@Autowired
	private JwtValidator jwtValidator;
	private enum Role{
		CUSTOMER,
		LOYALTY_CUSTOMER,
		ADMIN
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> 
					authorize
					//PUBLIC
					.requestMatchers(HttpMethod.GET, "/flights").permitAll()
					.requestMatchers(HttpMethod.POST, "/user/login").permitAll()
					.requestMatchers(HttpMethod.POST, "/user/signin").permitAll()
					//LOYALTY
		            .requestMatchers("/loyaltyUser/*").hasRole(Role.LOYALTY_CUSTOMER.toString())
		            //ADMIN
		            .requestMatchers("/admin/*").hasRole(Role.ADMIN.toString())
		            //CUSTOMER
		            .anyRequest().authenticated())
				.addFilterBefore(jwtValidator, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
}
