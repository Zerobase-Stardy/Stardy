package com.github.backend.config;


import com.github.backend.security.jwt.JwtAccessDeniedHandler;
import com.github.backend.security.jwt.JwtAuthenticationFilter;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final JwtEntryPoint jwtEntryPoint;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/courses/**/unlock", "/members/me/**").hasRole("USER")
			.antMatchers("/admin-management/**").hasRole("ADMIN")
			.anyRequest().permitAll();

		http.headers().frameOptions().disable();

		http.cors().configurationSource(corsConfigurationSource())
			.and()
			.httpBasic().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.formLogin().disable();
		http.logout().disable();

		http.exceptionHandling()
			.authenticationEntryPoint(jwtEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler);

		http.addFilterBefore(jwtAuthenticationFilter(),
			UsernamePasswordAuthenticationFilter.class);

		http.authenticationProvider(jwtAuthenticationProvider);

		return http.build();
	}


	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		return new JwtAuthenticationFilter(authenticationManager());
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}