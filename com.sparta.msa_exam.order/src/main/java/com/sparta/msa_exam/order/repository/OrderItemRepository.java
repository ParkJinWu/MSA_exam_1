package com.sparta.msa_exam.order.repository;

import com.sparta.msa_exam.order.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
