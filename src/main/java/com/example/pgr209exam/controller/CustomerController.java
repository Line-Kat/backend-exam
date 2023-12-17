package com.example.pgr209exam.controller;

import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.model.Customer;
import com.example.pgr209exam.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Page<Customer> getCustomers() {
        int pageNumber = 0;
        int pageSize = 5;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return customerService.getCustomers(pageable);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }

    @PutMapping("/{id}/address")
    public Customer addAddressToCustomer(@RequestBody Address address, @PathVariable Long id) {
        return customerService.addAddressToCustomer(id, address);
    }
}
