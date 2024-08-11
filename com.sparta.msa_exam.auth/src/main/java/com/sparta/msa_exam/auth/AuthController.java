package com.sparta.msa_exam.auth;

import com.sparta.msa_exam.auth.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    //@GetMapping("/auth/signIn")

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto){
        log.info("Received signUp request: {}", signUpDto);
        return ResponseEntity.ok(authService.signUp(signUpDto));

    }


}
