package com.example.TestSecurity.config;

import com.example.TestSecurity.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public securityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable());

        http
                .formLogin((login)->login.disable());

        http
                .httpBasic((basic)->basic.disable());

        http
                .oauth2Login(Customizer.withDefaults());

        http.
                oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(customOAuth2UserService)));

        http
                .authorizeHttpRequests((auth)->auth
                        .requestMatchers("/","/oauth2/**","/login/**").permitAll()
                        .anyRequest().authenticated());



        return http.build();
    }
}
