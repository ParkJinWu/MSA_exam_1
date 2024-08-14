package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.domain.User;
import com.sparta.msa_exam.auth.dto.AuthResponse;
import com.sparta.msa_exam.auth.dto.SignUpDto;
import com.sparta.msa_exam.auth.mapper.UserMapper;
import com.sparta.msa_exam.auth.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Date;

import javax.crypto.SecretKey;

@Service
public class AuthService {

    private final UserRepository userRepository;


    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;


    private final SecretKey secretKey;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, @Value("${service.jwt.secret-key}")String secretKey) {
        this.userRepository = userRepository;
        this.userMapper = new UserMapper();
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    // FIXME : Spring 심화 강의 응용 듣고 주석 삭제
    public AuthResponse createAccessToken(final String userId){
        // 1. 사용자 ID를 기준으로 데이터베이스에서 사용자를 찾습니다.
        return userRepository.findByUserId(userId)
                // 2. 사용자가 존재하는 경우만 JWT 생성 로직 수행 - Spring 심화 강의 응용
                .map(user -> AuthResponse.of(
                        //3. JWT 빌더를 사용하여 JWT를 생성
                        Jwts.builder()
                                //4. JWT에 사용자 ID를 클레임으로 추가
                                .claim("user_id", user.getUserId())
                                //5. JWT 발급자 설정
                                .issuer(issuer)
                                //6. JWT의 발급 시각을 현재 시간으로 설정
                                .issuedAt(new Date(System.currentTimeMillis()))
                                //7. JWT의 만료 시각을 현재 + 액세스 만료 시간으로 설정
                                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                                //8. JWT서명
                                .signWith(secretKey, SignatureAlgorithm.HS512)
                                //9. JWT를 문자열로 변환하여 반환
                                .compact())
                        //사용자가 존재하지 않을 경우 예외 발생
                ).orElseThrow();
    }

    // 회원 존재 여부 검증 비즈니스 로직
    // 주어진 userId로 사용자를 검색하여, 사용자가 존재하는지 여부를 반환합니다.
    public Boolean verifyUser(final String userId){
        // userId로 사용자를 조회하고, Optional이 비어 있지 않으면 true를 반환
        return userRepository.findByUserId(userId).isPresent();
    }

    // 회원 가입 비즈니스 로직
    // SignUpDto는 사용자 가입에 필요한 정보를 포함하고 있습니다.
    // SignUpDto를 기반으로 새로운 사용자를 생성하고, 이를 데이터베이스에 저장합니다.
    public void createUser(final SignUpDto signUpDto){
        // SignUpDto에서 사용자 ID를 추출하여 User 객체를 생성합니다.
        // 생성된 User 객체를 데이터베이스에 저장합니다.
        User user = userMapper.toEntity(signUpDto);
        userRepository.save(user);
        //userRepository.save(User.create(signUpDto.getUserId()));
    }

}
