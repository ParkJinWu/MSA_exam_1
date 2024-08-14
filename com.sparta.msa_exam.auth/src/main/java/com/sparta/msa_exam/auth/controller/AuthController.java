package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.dto.AuthResponse;
import com.sparta.msa_exam.auth.service.AuthService;
import com.sparta.msa_exam.auth.dto.SignUpDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

 //   private final String serverPort;

    /**
     * 로그인 API
     *
     * 사용자 ID를 통해 인증 토큰을 생성하고 반환합니다.
     * 요청 파라미터로 'user_id'를 받아서 AuthService를 호출하여 액세스 토큰을 생성합니다.
     *
     * @param userId 로그인할 사용자 ID
     * @return 생성된 액세스 토큰을 포함한 AuthResponse 객체
     */
    @GetMapping("/signIn")
    public ResponseEntity<?> createAuthenticationToken(final @RequestParam(value = "user_id") String userId){
        final AuthResponse response = authService.createAccessToken(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 회원가입 API
     *
     * 사용자 가입 요청을 처리하여 새로운 사용자를 생성합니다.
     * 요청 본문으로 'SignUpDto' 객체를 받아서 AuthService를 호출하여 사용자를 생성합니다.
     *
     * @param signUpDto 사용자 가입에 필요한 정보를 담고 있는 DTO 객체
     * @return 회원가입 성공 여부를 나타내는 boolean 값
     */
    @PostMapping("/signUp")
    public ResponseEntity<?> createUser(@RequestBody @Valid SignUpDto signUpDto){
        if(signUpDto.getUserId() == null || signUpDto.getUserId().isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("userId는 필수 값 입니다.");
        }
        authService.createUser(signUpDto);
        return ResponseEntity.ok(true);
    }

    /**
     * 사용자 존재 여부 검증 API
     *
     * 사용자 ID를 통해 해당 사용자가 존재하는지 확인합니다.
     * 요청 파라미터로 'user_id'를 받아서 AuthService를 호출하여 사용자의 존재 여부를 검증합니다.
     *
     * @param userId 검증할 사용자 ID
     * @return 사용자 존재 여부를 나타내는 boolean 값
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(final @RequestParam(value = "user_id") String userId){
        Boolean response = authService.verifyUser(userId);
        log.info("############");
        //userId = "dd";
        String responseString = response != null && response ? "Welcome :" +userId + " !!!" : "존재하지 않은 사용자 입니다.";

        return ResponseEntity.ok(responseString);
    }


}
