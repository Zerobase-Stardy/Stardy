package com.github.backend.config;

import com.github.backend.service.impl.CustomOAuth2UserService;
import com.github.backend.service.impl.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable();

        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().   sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             .and()
                .authorizeRequests()
                .antMatchers("/token/** ", "/h2").permitAll()
                .anyRequest().authenticated()
             .and()
                .csrf()
                .ignoringAntMatchers("/h2/**")
             .and()
                .oauth2Login().loginPage("/token/expired")
                .successHandler(successHandler)
                .userInfoEndpoint().userService(oAuth2UserService);

        http.addFilterBefore(new JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
    }
}
