package com.sparta.msa_exam.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/auth/signIn")
    //원래는 POST로 해야된다.
    // ? : Wildcard type ➡️ 어떤 타입이든 받는다.
    public ResponseEntity<?> createAuthToken(@RequestParam String user_id){
        // ok ➡️ HTTP 200
        // AuthService에서 받아온 accesToken값
        // yser_id로 Token을 만들고, access_token 키값으로 묶는다.
        return ResponseEntity.ok(new AuthResponse(authService.createAccessToken(user_id)));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    //Response
    static class AuthResponse{
        private String accessToken;
    }
}
