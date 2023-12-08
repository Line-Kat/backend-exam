package com.example.pgr209exam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order-sequence-generator")
    @SequenceGenerator(name = "order-sequence-generator", sequenceName = "order-sequence", allocationSize = 1)
    @Column(name = "order_id")
    private Long orderId = 0L;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties("orders")
    private Customer customer;

    //unidirectional
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_machine",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "machine_id")
    )
    private List<Machine> machines = new ArrayList<>();
}
