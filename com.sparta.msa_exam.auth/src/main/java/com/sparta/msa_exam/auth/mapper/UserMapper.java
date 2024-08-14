package com.sparta.msa_exam.auth.mapper;

import com.sparta.msa_exam.auth.domain.User;
import com.sparta.msa_exam.auth.dto.SignUpDto;

public class UserMapper {

    /**
     * SignUpDto를 User 엔티티로 변환
     *
     * @param signUpDto 회원가입 요청 DTO
     * @return 변환된 User 엔티티
     */
    public static User toEntity(SignUpDto signUpDto) {
        // DTO에서 필요한 정보를 추출하여 엔티티를 생성합니다.
        return User.create(signUpDto.getUserId());
    }

}
