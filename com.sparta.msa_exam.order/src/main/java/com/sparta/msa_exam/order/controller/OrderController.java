package com.sparta.msa_exam.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    // Test API
    @GetMapping("/test")
    public String getOrder() {
        return "Order details";
    }

    // POST /order : 주문 추가 API



    // PUT /order/{orderId} : 주문에 상품을 추가하는 API



    // GET /order/{orderId} : 주문 단건 조회 API

}
