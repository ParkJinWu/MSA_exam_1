package com.sparta.msa_exam.gateway;


import com.sparta.msa_exam.gateway.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class LocalJwtAuthenticationFilter implements GlobalFilter {
    /*
    * 동일한 JWT secretKey가 있어야지 로그인 여부를 식별 가능.
    * 따라서 Gateway yml에 secretKey를 넣고, @Value 어노테이션을 사용하여 가져옴
    */
    private static final String EXCLUDED_PREFIX = "/auth";

    private final String secretKey;

    private final AuthService authService;

    public LocalJwtAuthenticationFilter(@Value("${service.jwt.secret-key}")String secretKey, @Lazy AuthService authService) {
        this.secretKey = secretKey;
        this.authService = authService;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 로그인 페이지 필터 X
        String path = exchange.getRequest().getURI().getPath();
        //TODO
        if (path.startsWith(EXCLUDED_PREFIX)) {
            log.info("@@@@" +path);
            return chain.filter(exchange);  // /auth 경로는 필터를 적용하지 않음
        }

        // Token 가져오기
        String token = extractToken(exchange);

        /*
         * 외부 요청 보호
         * Oauth2,JWT 기반으로 인증/인가를 구성하여 인가 없이 상품 서비스, 주문 서비스를 호출할 때
         * 401 HTTP Status Code를 응답
         */
        if (token == null || !validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //정상적인 Token은 다음 Chain으로 이동
        return chain.filter(exchange);
    }

    private String extractToken(ServerWebExchange exchange) {
        // Request Header 에서 Authorization Key 로 설정된 된 값을 불러옵니다.
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        // 값이 존재하며, Bearer {token} 형태로 시작할 경우
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 값중 앞부분 ('Bearer ') 을 제거하고 응답합니다.
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            // String -> SecretKey 변환 작업
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            // JWT 에 설정된 정보를 불러옵니다.
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build().parseSignedClaims(token);

            // JWT 값 중 Payload 부분에 user_id 로 설정된 값이 있는 경우
            if (claimsJws.getPayload().get("user_id") != null) {
                // user_id 추출 로직
                String userId = claimsJws.getPayload().get("user_id").toString();
                log.info("@@@@" +userId);
                // user_id 값으로 해당 유저가 회원가입 한 유저인지 인증 서비스를 통해 확인합니다.
                return authService.verifyUser(userId);
            } else {
                return false;
            }
        }catch (Exception e) {
            return false;
        }
    }


}
