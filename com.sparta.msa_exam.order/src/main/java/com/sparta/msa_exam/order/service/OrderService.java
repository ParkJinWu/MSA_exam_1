package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.mapper.OrderMapper;
import com.sparta.msa_exam.order.order.Order;
import com.sparta.msa_exam.order.order.OrderItem;
import com.sparta.msa_exam.order.repository.OrderItemRepository;
import com.sparta.msa_exam.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Order order = OrderMapper.toEntity(orderRequestDto);
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toDto(savedOrder);
    }

    @Transactional
    public OrderResponseDto addProductToOrder(Long orderId, Long productId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            OrderItem orderItem = OrderItem.builder()
                    .productId(productId)
                    .order(order)
                    .build();
            order.getProductIds().add(orderItem);
            orderItemRepository.save(orderItem);
            return OrderMapper.toDto(order);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        return orderOpt.map(OrderMapper::toDto).orElse(null);
    }

}
