package com.github.backend.config;
import com.github.backend.security.jwt.JwtAuthenticationFilter;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;
import com.github.backend.security.oauth.OAuth2SuccessHandler;
import com.github.backend.service.impl.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final JwtEntryPoint jwtEntryPoint;
	private final AuthenticationConfiguration authenticationConfiguration;

	private final OAuth2SuccessHandler oAuth2SuccessHandler;

	private final CustomOAuth2UserService oAuth2UserService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.httpBasic().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.formLogin().disable();
		http.logout().disable();

		http.oauth2Login()
			.successHandler(oAuth2SuccessHandler)
				.userInfoEndpoint()
					.userService(oAuth2UserService);




		http.exceptionHandling()
			.authenticationEntryPoint(jwtEntryPoint);

		http.addFilterBefore(jwtAuthenticationFilter(),
			UsernamePasswordAuthenticationFilter.class);

		http.authenticationProvider(jwtAuthenticationProvider);

		http.authorizeRequests()
				.antMatchers("/member/**" ,"/oauth/logout")
				.authenticated()
				.anyRequest().permitAll();

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


}