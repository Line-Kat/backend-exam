package com.example.pgr209exam.service;

import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.model.Customer;
import com.example.pgr209exam.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {

        return customerRepository.findById(id).orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
