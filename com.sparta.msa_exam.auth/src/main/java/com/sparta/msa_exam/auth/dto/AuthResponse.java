package com.sparta.msa_exam.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * User 로그인 시 응답 객체
 * 인증 과정에서 서버가 클라이언트에게 반환하는 응답 객체 정의
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String accessToken;

    /**
     * 액세스 토큰을 사용하여 AuthResponse 객체를 생성하는 정적 팩토리 메서드입니다.
     *
     * @param accessToken 생성할 액세스 토큰 값입니다.
     * @return 액세스 토큰을 포함한 AuthResponse 객체입니다.
     */
    public static AuthResponse of(String accessToken) {
        return AuthResponse.builder() // AuthResponse의 빌더를 사용하여 객체를 생성
                .accessToken(accessToken)
                .build();
    }
}
