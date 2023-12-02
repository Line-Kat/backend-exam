package com.example.pgr209exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address-sequence-generator")
    @SequenceGenerator(name = "address-sequence-generator", sequenceName = "address-sequence", allocationSize = 1)
    @Column(name = "address_id")
    private Long addressId = 0L;

    @ManyToMany(mappedBy = "addresses", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Customer> customers = new ArrayList<>();

}
