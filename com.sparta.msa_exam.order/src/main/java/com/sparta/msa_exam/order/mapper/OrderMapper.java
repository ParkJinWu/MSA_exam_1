package com.sparta.msa_exam.order.mapper;

import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.order.Order;
import com.sparta.msa_exam.order.order.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    // DTO를 엔티티로 변환
    public static Order toEntity(OrderRequestDto orderRequestDto) {
        Order order = Order.builder()
                .name(orderRequestDto.getName())
                .build();

        List<OrderItem> orderItems = orderRequestDto.getProductIds().stream()
                .map(productId -> OrderItem.builder()
                        .productId(productId)
                        .order(order)
                        .build())
                .collect(Collectors.toList());

        order.setProductIds(orderItems);
        return order;
    }

    // 엔티티를 DTO로 변환
    public static OrderResponseDto toDto(Order order) {
        List<Long> productIds = order.getProductIds().stream()
                .map(OrderItem::getProductId)
                .collect(Collectors.toList());

        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .name(order.getName())
                .productIds(productIds)
                .build();
    }
}
