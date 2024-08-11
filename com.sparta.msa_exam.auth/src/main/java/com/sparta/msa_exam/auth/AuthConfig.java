package com.sparta.msa_exam.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // 요청에 대한 접근 권한을 설정합니다.
                .authorizeRequests(authorize -> authorize
                        // /auth/signIn 경로에 대한 접근을 허용
                        .requestMatchers("/auth/signIn").permitAll()
                        .requestMatchers("/auth/signUp").permitAll()
                        // 그 외의 모든 요청은 인증이 필요
                        .anyRequest().authenticated()
                )
                // 세션 관리 정책을 정의합니다. 여기서는 세션을 사용하지 않도록 STATELESS로 설정합니다.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // 설정된 보안 필터 체인을 반환합니다.
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
