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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer-sequence-generator")
    @SequenceGenerator(name = "customer-sequence-generator", sequenceName = "customer-sequence", allocationSize = 1)
    @Column(name = "customer_id")
    private Long customerId = 0L;
}
