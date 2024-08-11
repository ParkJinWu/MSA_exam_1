package com.sparta.msa_exam.auth;

import com.sparta.msa_exam.auth.auth.User;
import com.sparta.msa_exam.auth.dto.SignUpDto;
import com.sparta.msa_exam.auth.jwt.JwtUtil;
import com.sparta.msa_exam.auth.mapper.UserMapper;
import com.sparta.msa_exam.auth.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }


    public String signUp(SignUpDto signUpDto) {
        // 사용자 이름이 이미 존재하는지 확인
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        if(userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        // 비밀번호를 암호화
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        //signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        // DTO를 엔티티로 변환
        User user = UserMapper.toEntity(signUpDto, encodedPassword);

        // 사용자 저장
        userRepository.save(user);



        return signUpDto.getUsername();
    }
}
