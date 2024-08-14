package com.sparta.msa_exam.gateway.client;

import com.sparta.msa_exam.gateway.service.AuthService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Feign 클라이언트 정의
@FeignClient(name = "auth-service")
//public interface AuthClient extends AuthService {
public interface AuthClient {
    @GetMapping("/auth/verify") // 유저 검증 API
    Boolean verifyUser(@RequestParam(value = "user_id") String userId);
}
