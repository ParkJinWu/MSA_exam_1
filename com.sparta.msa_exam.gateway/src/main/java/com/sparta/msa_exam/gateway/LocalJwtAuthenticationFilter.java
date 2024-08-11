package com.sparta.msa_exam.gateway;

import com.sparta.msa_exam.gateway.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
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

//    @Value("${service.jwt.secret-key}")
//    private String secretKey;
    private final JwtUtil jwtUtil;

    public LocalJwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 로그인 페이지 필터 X
        String path = exchange.getRequest().getURI().getPath();
        if(path.equals("/auth/signIn")){
            return chain.filter(exchange);
        }

        // Token 가져오기
        //String token = extractToken(exchange);
        String token = jwtUtil.getJwtToken(exchange.getRequest());

        /*
         * 외부 요청 보호
         * Oauth2,JWT 기반으로 인증/인가를 구성하여 인가 없이 상품 서비스, 주문 서비스를 호출할 때
         * 401 HTTP Status Code를 응답
         */
        if (token == null || !jwtUtil.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        //정상적인 Token은 다음 Chain으로 이동
        return chain.filter(exchange);
    }


}
