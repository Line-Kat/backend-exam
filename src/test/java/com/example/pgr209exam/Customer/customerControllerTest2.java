package com.example.pgr209exam.Customer;


import com.example.pgr209exam.controller.CustomerController;
import com.example.pgr209exam.model.Customer;
import com.example.pgr209exam.repository.CustomerRepository;
import com.example.pgr209exam.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class customerControllerTest2 {

    //@Value(value = "${local.server.port}")

    //@LocalServerPort
    private int port;


    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    // Clean up database
    @AfterEach
    void cleanup() {
        verifyNoMoreInteractions(customerRepository);
        //customerRepository.deleteAll();
    }

    @Test
    public void testGetCustomers(){
        // med constructor:
        // Customer customer = new Customer("testName", "testEmail@email.com");
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
        assertEquals(1, customers.length);
        assertEquals(customer.getCustomerId(), customers[0].getCustomerId());
        assertEquals(customer.getCustomerName(), customers[0].getCustomerName());
        assertEquals(customer.getCustomerEmail(), customers[0].getCustomerEmail());
    }

    @Test
    public void testGetCustomerById(){
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
        assertNotNull(retrievedCustomer);
        assertEquals(customer.getCustomerName(), retrievedCustomer.getCustomerName());
        assertEquals(customer.getCustomerEmail(), retrievedCustomer.getCustomerEmail());
    }

    @Test
    public void testCreateCustomer(){
        Customer customer = new Customer();
        customer.setCustomerId(12345L);
        customer.setCustomerName("testName");
        customer.setCustomerEmail("testCustomer@email.com");
        //customerRepository.save(customer);

        ResponseEntity<Customer> responseEntity = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/api/customer",
                customer,
                Customer.class
        );

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Customer createdCustomer = responseEntity.getBody();
        assertNotNull(createdCustomer);
        assertNotNull(createdCustomer.getCustomerId());
        assertEquals(customer.getCustomerName(), createdCustomer.getCustomerName());
        assertEquals(customer.getCustomerEmail(), createdCustomer.getCustomerEmail());
    }

    @Test
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
                "http://localhost:" + port + "/api/customer",
                HttpMethod.PUT,
                new HttpEntity<>(updatedCustomer),
                Customer.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Customer retrievedCustomer = responseEntity.getBody();
        assertNotNull(retrievedCustomer);
        assertEquals(updatedCustomer.getCustomerName(), retrievedCustomer.getCustomerName());
        assertEquals(updatedCustomer.getCustomerEmail(), retrievedCustomer.getCustomerEmail());

        // Verify that the repository save method was called
        verify(customerRepository, times(1)).save(ArgumentMatchers.any(Customer.class));
    }


    @Test
    public void testDeleteCustomerById() {
        Customer customer = new Customer();
        customer.setCustomerId(12345L);
        customer.setCustomerName("testName");
        customer.setCustomerEmail("testCustomer@email.com");
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).deleteById(customer.getCustomerId());

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/api/customer/" + customer.getCustomerId(),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(customerRepository, times(1)).deleteById(customer.getCustomerId());
    }

}