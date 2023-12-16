package com.example.pgr209exam.Customer;
import com.example.pgr209exam.model.Customer;
import com.example.pgr209exam.repository.CustomerRepository;
import com.example.pgr209exam.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@SpringBootApplication(scanBasePackages = "com.example.pgr209exam")
public class CustomerControllerInjectMocksTests {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCustomers() {
        Page<Customer> mockedPage = mock(Page.class);
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(mockedPage);
        Page<Customer> result = customerService.getCustomers(PageRequest.of(0, 5));
        verify(customerRepository, times(1)).findAll(any(Pageable.class));
        assertEquals(mockedPage, result);
    }

    @Test
    public void testUpdateCustomer(){

        long customerId = 1;
        // OLD CUSTOMER
        Customer customer1 = new Customer();
        customer1.setCustomerId(customerId);
        customer1.setCustomerName("testOld customer");

        // UPDATED CUSTOMER
        Customer customer2 = new Customer();
        customer2.setCustomerId(customerId);
        customer2.setCustomerName("test new customer");


        when(customerRepository.save(customer1)).thenReturn(customer2);
        Customer result1 = customerService.updateCustomer(customer1);
        verify(customerRepository, times(1)).save(customer1);
        assertEquals(customer2, result1);
    }
}
