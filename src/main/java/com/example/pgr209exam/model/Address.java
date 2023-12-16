package com.example.pgr209exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address-sequence-generator")
    @SequenceGenerator(name = "address-sequence-generator", sequenceName = "address-sequence", allocationSize = 1)
    @Column(name = "address_id")
    private Long addressId = 0L;

    @Column(name = "address_name")
    private String addressName;

    public Address(String addressName) {
        this.addressName = addressName;
    }

    @ManyToMany(mappedBy = "addresses", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Customer> customers = new ArrayList<>();
}
