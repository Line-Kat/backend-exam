package com.example.pgr209exam.wrapper;

import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressWrapper {
    private Customer customer;
    private List<Address> addresses;
}
