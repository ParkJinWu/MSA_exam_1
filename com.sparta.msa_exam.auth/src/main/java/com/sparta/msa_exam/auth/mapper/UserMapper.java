package com.sparta.msa_exam.auth.mapper;

import com.sparta.msa_exam.auth.auth.User;
import com.sparta.msa_exam.auth.dto.SignUpDto;

public class UserMapper {

    /**
     * SignUpDto를 User 엔티티로 변환합니다.
     *
     * @param signUpDto 회원가입 요청 DTO
     * @return 변환된 User 엔티티
     */
    public static User toEntity(SignUpDto signUpDto, String encodedPassword) {
        return User.builder()
                .username(signUpDto.getUsername())
                .password(encodedPassword)
                .email(signUpDto.getEmail())
                .build();
    }

}
