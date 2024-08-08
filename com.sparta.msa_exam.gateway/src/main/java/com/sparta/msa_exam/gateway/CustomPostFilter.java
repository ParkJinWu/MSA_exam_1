package com.sparta.msa_exam.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class CustomPostFilter implements GlobalFilter, Ordered {
    private static final Logger logger = Logger.getLogger(CustomPreFilter.class.getName());


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Response 가져오기
            ServerHttpResponse response = exchange.getResponse();

            logger.info("Post Filter :  Response status code is " + response.getStatusCode());



        }));
    }

    @Override
    public int getOrder() {
        // 최하위 Order
        return Ordered.LOWEST_PRECEDENCE;
    }
}
