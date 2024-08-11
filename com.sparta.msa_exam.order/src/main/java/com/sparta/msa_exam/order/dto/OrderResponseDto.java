package com.sparta.msa_exam.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long orderId;           // 주문 ID
    private String name;            // 주문 이름
    private List<Long> productIds;  // 주문에 포함된 상품 ID 목록
}
