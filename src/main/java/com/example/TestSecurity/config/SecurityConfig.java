package com.example.TestSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.OAuth2ClientDsl;
import org.springframework.security.config.annotation.web.OAuth2LoginDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers("/","/login","/join","/joinProc").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("USER","ADMIN")
                        .anyRequest().authenticated()
                );


        //login
        http
                .httpBasic(Customizer.withDefaults());


        //logout
        http
                .logout((auth)->auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        //csrf
        // http.csrf((auth)->auth.disable());


        //세션 고정 보호
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().newSession());


        //다중 로그인 관리
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));



        return http.build();
    }


    //bcrypt
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
