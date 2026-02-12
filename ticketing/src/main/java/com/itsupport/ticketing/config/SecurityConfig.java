package com.itsupport.ticketing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.itsupport.ticketing.security.CustomLoginSuccessHandler;
import com.itsupport.ticketing.security.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;
	private final CustomLoginSuccessHandler successHandler;

	public SecurityConfig(CustomUserDetailsService userDetailsService, CustomLoginSuccessHandler successHandler) {
		this.userDetailsService = userDetailsService;
		this.successHandler = successHandler;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/support/**").hasRole("SUPPORT")
						.requestMatchers("/user/**").hasRole("USER")
						.requestMatchers("/change-password").authenticated()
						.requestMatchers("/files/**").authenticated()
						.anyRequest().authenticated())

				.formLogin(form -> form
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.successHandler(successHandler)
						.permitAll())

				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout")
						.permitAll())
						
				.exceptionHandling(ex -> ex
						.accessDeniedPage("/error"));
						
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder)
			throws Exception {

		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		return builder.build();
	}
}
