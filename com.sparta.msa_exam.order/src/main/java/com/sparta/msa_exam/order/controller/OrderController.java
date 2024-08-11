package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Test API
//    @GetMapping("/test")
//    public String getOrder() {
//        return "Order details";
//    }

    /*주문 추가 API
    * 주문 Entity : order_id = Long(PK, AutoIncrement)
    *              name : String
    *              product_idx = List<주문 매핑 상품 Entity>*/
    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto requestDto) {
        return orderService.createOrder(requestDto);
    }



    // PUT /order/{orderId} : 주문에 상품을 추가하는 API
    @PutMapping("/{orderId}")
    public OrderResponseDto addProductToOrder(@PathVariable Long orderId, @RequestParam Long productId) {
        return orderService.addProductToOrder(orderId,productId);
    }


    // GET /order/{orderId} : 주문 단건 조회 API
    @GetMapping("/{orderId}")
    public OrderResponseDto getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }
}
