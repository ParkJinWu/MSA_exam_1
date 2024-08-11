package com.sparta.msa_exam.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {
    //private Long orderId;
    private String name;
    private List<Long> productIds;
}
