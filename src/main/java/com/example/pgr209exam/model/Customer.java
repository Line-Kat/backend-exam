package com.example.pgr209exam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_email")
    private String customerEmail;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "customer_address",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses = new ArrayList<>();

    public void addAddress(Address address){
        this.addresses.add(address);
        if (address.getCustomers() == null) {
            address.setCustomers(new ArrayList<>());
        }
        address.getCustomers().add(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return Objects.equals(customerId, customer.customerId) &&
                Objects.equals(customerName, customer.customerName) &&
                Objects.equals(customerEmail, customer.customerEmail);
    }

    @Override
    public int hashCode(){
        return Objects.hash(customerId, customerName, customerEmail);
    }
}
