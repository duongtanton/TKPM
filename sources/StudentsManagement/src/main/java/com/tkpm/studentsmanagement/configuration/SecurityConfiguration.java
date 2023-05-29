package com.tkpm.studentsmanagement.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @Component
// class CustomAccessDeniedHandler implements AccessDeniedHandler {

// 	@Override
// 	public void handle(HttpServletRequest request, HttpServletResponse response,
// 			AccessDeniedException exc) throws IOException {
// 	}
// }

// @Component
// class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
// 	@Override
// 	public void commence(HttpServletRequest request, HttpServletResponse response,
// 			AuthenticationException authException) throws IOException {
// 	}
// }

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private UserDetailsService userDetailsService;

	private Integer maximumSession = 1;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// CSRF ( Cross Site Request Forgery) là kĩ thuật tấn công bằng cách sử dụng
		// quyền chứng thực của người sử dụng đối với 1 website khác
		http.csrf().disable();
		http.cors().disable();

		http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("dist/**", "css/**", "src/**", "assets/**", "not-permission/**",
								"forgot-password/**")
						.permitAll()
						.requestMatchers("/**").hasAnyAuthority("USER", "ADMIN"))
				.formLogin(
						form -> form
								.loginPage("/login")
								.defaultSuccessUrl("/")
								.permitAll())
				.logout(
						logout -> logout
								.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
								.logoutSuccessUrl("/login")
								.permitAll());

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
				.maximumSessions(this.maximumSession).expiredUrl("/login");

		// http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
		// .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		return http.build();
	}

	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth) throws
	// Exception {
	// auth
	// .userDetailsService(userDetailsService)
	// .passwordEncoder(passwordEncoder());
	// }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}