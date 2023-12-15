package com.example.pgr209exam.Customer;

import com.example.pgr209exam.controller.CustomerController;
import com.example.pgr209exam.model.Customer;
import com.example.pgr209exam.model.Order;
import com.example.pgr209exam.repository.CustomerRepository;
import com.example.pgr209exam.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@SpringBootApplication(scanBasePackages = "com.example.pgr209exam")
public class CustomerServiceTests {
    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;
    private CustomerController customerController;

    @Before
    public void setUp(){
        customerService = mock(CustomerService.class);
        customerRepository = mock(CustomerRepository.class);
        customerController = new CustomerController(customerService);
    }

    // TEST FOR testGetCustomers() is in CustomerControllerInjectMocksTests because it uses injectMocks instead of Mock
    // each class can use different annotations that make sense for each class/method -s testing requirements.


    @Test
    public void testGetCustomerById(){
        long testCustomerId = 123;
        Customer expectedCustomer = new Customer();
        expectedCustomer.setCustomerId(testCustomerId);
        expectedCustomer.setCustomerName("test1");
        expectedCustomer.setCustomerEmail("test@email.com");

        when(customerService.getCustomerById(testCustomerId)).thenReturn(expectedCustomer);
        Customer response = customerController.getCustomerById(testCustomerId);
        assertEquals(expectedCustomer, response);
    }

    @Test
    public void testCreateCustomer(){
        Customer inputCustomer = new Customer();
        inputCustomer.setCustomerId(null);
        inputCustomer.setCustomerName("test1");
        inputCustomer.setCustomerEmail("test@email.com");

        when(customerService.createCustomer(inputCustomer)).thenReturn(inputCustomer);

        // changed CustomerController createAppointmentCustomer() to createCustomer()
        Customer response = customerController.createCustomer(inputCustomer);

        assertNotNull(response);
        assertEquals(inputCustomer.getCustomerName(), response.getCustomerName());
        assertEquals(inputCustomer.getCustomerEmail(), response.getCustomerEmail());

    }
    // TEST FOR testUpdateCustomer() is in CustomerControllerInjectMocksTests because it uses injectMocks instead of Mock
    // each class can use different annotations that make sense for each class/method -s testing requirements.



    @Test
    public void testDeleteCustomerById(){
        long testCustomerId = 123;
        doNothing().when(customerService).deleteCustomerById(testCustomerId);
        customerController.deleteCustomerById(testCustomerId);

        verify(customerService, times(1)).deleteCustomerById(testCustomerId);
    }

    @Test
    public void testDeleteCustomerByIdException(){
        long testCustomerId = 123450;

        doThrow(new IllegalArgumentException("Customer not found")).when(customerService).deleteCustomerById(testCustomerId);
        assertThrows(IllegalArgumentException.class, () -> customerController.deleteCustomerById(testCustomerId));
        verify(customerService, times(1)).deleteCustomerById(testCustomerId);
    }
}


