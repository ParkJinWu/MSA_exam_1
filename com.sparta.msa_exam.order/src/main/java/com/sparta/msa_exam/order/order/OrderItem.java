package com.sparta.msa_exam.order.order;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId;

}
