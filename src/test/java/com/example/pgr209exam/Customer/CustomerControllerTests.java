package com.example.pgr209exam.Customer;

import com.example.pgr209exam.controller.CustomerController;
import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.model.Customer;
import com.example.pgr209exam.model.Subassembly;
import com.example.pgr209exam.repository.CustomerRepository;
import com.example.pgr209exam.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CustomerControllerTests {

    @Value(value = "${local.server.port}")

    @LocalServerPort
    private int port;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @org.junit.jupiter.api.Test
    public void testGetCustomers(){
        Customer customer = new Customer();
        customer.setCustomerId(12345L);
        customer.setCustomerName("testName");
        customer.setCustomerEmail("testCustomer@email.com");
        customerRepository.save(customer);

        ResponseEntity<Customer[]> responseEntity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/api/customer",
                Customer[].class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Customer[] customers = responseEntity.getBody();
        assertNotNull(customers);
    }

    @org.junit.jupiter.api.Test
    public void testGetCustomerById() {
        Customer customer = new Customer();
        customer.setCustomerId(12345L);
        customer.setCustomerName("testName");
        customer.setCustomerEmail("testCustomer@email.com");
        customerRepository.save(customer);

        ResponseEntity<Customer> responseEntity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/api/customer/" + customer.getCustomerId(),
                Customer.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Customer retrievedCustomer = responseEntity.getBody();
    }

    @org.junit.jupiter.api.Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(12345L);
        customer.setCustomerName("testName");
        customer.setCustomerEmail("testCustomer@email.com");
        customerRepository.save(customer);

        ResponseEntity<Customer> responseEntity = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/api/customer",
                customer,
                Customer.class
        );
        Customer createdCustomer = responseEntity.getBody();
    }
    @org.junit.jupiter.api.Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(12345L);
        customer.setCustomerName("testName");
        customer.setCustomerEmail("testCustomer@email.com");
        when(customerRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(customer);

        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerId(customer.getCustomerId());
        updatedCustomer.setCustomerName("testUpdateName");
        updatedCustomer.setCustomerEmail("testUpdate@email.com");


        ResponseEntity<Customer> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/api/customer/" + customer.getCustomerId(),
                HttpMethod.PUT,
                new HttpEntity<>(updatedCustomer),
                Customer.class
        );

        Customer retrievedCustomer = responseEntity.getBody();
        assertNotNull(retrievedCustomer);
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(12345L);
        customer.setCustomerName("testName");
        customer.setCustomerEmail("testCustomer@email.com");
        customerRepository.save(customer);

        testRestTemplate.delete("http://localhost:" + port + "/api/customer/" + customer.getCustomerId());
        assertFalse(customerRepository.existsById(customer.getCustomerId()));
    }

    @Autowired
    private CustomerController customerController;

    @Test
    public void addAddressToCustomer_newAddressIsAddedToCustomer_shouldReturnNewAddress() {
        Customer customer = new Customer();
        customer.setCustomerId(12345L);
        customer.setCustomerName("Mari");
        customer.setCustomerEmail("testCustomer@email.com");
        when(customerRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(customer);

        Assertions.assertEquals("Mari", customer.getCustomerName());

        String newAddressName = "Granlia";
        Address address = new Address(newAddressName);
        Customer returnedCustomer = customerController.addAddressToCustomer(customer, address);

        int numberOfAddresses = returnedCustomer.getAddresses().size();
        Assertions.assertEquals(1, numberOfAddresses);

        List<Address> addresses = returnedCustomer.getAddresses();
        Address newAddress = addresses.get(0);

        Assertions.assertEquals(newAddressName, newAddress.getAddressName());
    }
}

