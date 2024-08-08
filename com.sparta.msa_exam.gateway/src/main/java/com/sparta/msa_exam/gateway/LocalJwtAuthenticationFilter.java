package com.sparta.msa_exam.gateway;

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

    @Value("${service.jwt.secret-key}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 로그인 페이지 필터 X
        String path = exchange.getRequest().getURI().getPath();
        if(path.equals("/auth/signIn")){
            return chain.filter(exchange);
        }

        // Token 가져오기
        String token = extractToken(exchange);


        /*
         * 외부 요청 보호
         * Oauth2,JWT 기반으로 인증/인가를 구성하여 인가 없이 상품 서비스, 주문 서비스를 호출할 때
         * 401 HTTP Status Code를 응답
         */
        if (token == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        //정상적인 Token은 다음 Chain으로 이동
        return chain.filter(exchange);
    }

    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        //Oauth2 validation
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // 순수 Token 반환
        }
        return null; // 잘못된 Token은 NULL 반환
    }


    private boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build().parseSignedClaims(token);
            log.info("#####payload :: " + claimsJws.getPayload().toString());


            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
