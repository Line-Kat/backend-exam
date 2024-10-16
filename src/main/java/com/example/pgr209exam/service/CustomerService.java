package com.example.pgr209exam.service;

import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.model.Customer;
import com.example.pgr209exam.model.Order;
import com.example.pgr209exam.repository.AddressRepository;
import com.example.pgr209exam.repository.CustomerRepository;
import com.example.pgr209exam.wrapper.CustomerAddressWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    public Page<Customer> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer getCustomerById(Long id) { return customerRepository.findById(id).orElse(null); }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer createCustomerWithAddress(CustomerAddressWrapper wrapper) {
        Customer customer = wrapper.getCustomer();
        List<Address> addresses = wrapper.getAddresses();

        if (addresses != null){
            addresses.forEach(address -> {
                Address existingAddress = addressRepository.findById(address.getAddressId())
                        .orElseGet(() -> addressRepository.save(address));
                customer.addAddress(existingAddress);
            });
        }
        return customerRepository.save(customer);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer addAddressToCustomer(Long id, Address address) {
        Customer customer = getCustomerById(id);
        List<Address> addresses = customer.getAddresses();
        addresses.add(address);
        customer.setAddresses(addresses);

        return customerRepository.save(customer);
    }

    public Customer addOrder(Long id, Order order) {
        Customer customer = getCustomerById(id);
        List<Order> orders = customer.getOrders();
        orders.add(order);
        customer.setOrders(orders);

        return customerRepository.save(customer);
    }
}
