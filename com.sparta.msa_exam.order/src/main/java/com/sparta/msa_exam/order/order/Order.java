package com.sparta.msa_exam.order.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String name;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> productIds = new ArrayList<>();

    public void setProductIds(List<OrderItem> orderItems) {
        this.productIds = productIds;
    }

    public void addProductId(OrderItem orderItem) {
        this.productIds.add(orderItem);
        orderItem.setOrder(this);
    }
}
