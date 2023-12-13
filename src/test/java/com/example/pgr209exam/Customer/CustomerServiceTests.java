package com.example.pgr209exam.Customer;

import com.example.pgr209exam.controller.CustomerController;
import com.example.pgr209exam.model.Customer;
import com.example.pgr209exam.repository.CustomerRepository;
import com.example.pgr209exam.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {
    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;
    private CustomerController customerController;

    @Before
    public void setUp(){
        customerService = mock(CustomerService.class);
        customerController = new CustomerController(customerService);
        customerRepository = mock(CustomerRepository.class);
    }

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

        /*
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ----------med constructor:----------------
        long customerId = 123;
        Customer expectedCustomer = new Customer(customerId, "testName", "test@email.com");
        when(customerService.getCustomerById(customerId)).thenReturn(expectedCustomer);
        ResponseEntity<Customer> response = customerController.getCustomerById(customerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCustomer, response.getBody());
         */
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

    @Test
    public void testUpdateCustomer(){
        long customerId = 123;
        // Old Customer
        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerId(customerId);
        existingCustomer.setCustomerName("testOldName");
        existingCustomer.setCustomerEmail("testOld@email.com");

        // Create a new
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerId(customerId);
        updatedCustomer.setCustomerName("testUpdateName");
        updatedCustomer.setCustomerEmail("testUpdate@email.com");



        when(customerRepository.save(existingCustomer)).thenReturn(updatedCustomer);
        Customer result = customerService.updateCustomer(existingCustomer);
        verify(customerRepository, times(1)).save(existingCustomer);
        assertEquals(updatedCustomer, result);

        // Print debug
        //System.out.println("before: " + customerRepository.save(existingCustomer));
        // when ..
        // customer
        // Debugging
        //System.out.println("After: " + customerRepository.save(existingCustomer));
        // verify code...
        //System.out.println("Result: " + result);
        // assertEquals...


    }




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


