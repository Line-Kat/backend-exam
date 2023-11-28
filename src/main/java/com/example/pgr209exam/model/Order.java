package com.example.pgr209exam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order-sequence-generator")
    @SequenceGenerator(name = "order-sequence-generator", sequenceName = "order-sequence", allocationSize = 1)
    @Column(name = "order_id")
    private Long orderId = 0L;
}
