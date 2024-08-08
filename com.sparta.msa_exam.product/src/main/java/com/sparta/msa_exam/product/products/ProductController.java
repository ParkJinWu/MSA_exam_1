package com.sparta.msa_exam.product.products;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    //Load Balancing Test API
    @Value("${server.port}") // 애플리케이션이 실행 중인 포트를 주입받습니다.
    private String serverPort;

    @GetMapping("/test")
    public String getProduct() {
        return "Product info!!!!! From port : " + serverPort;
    }

    //상품 목록 조회 API

}
