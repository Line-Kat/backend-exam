package com.example.pgr209exam.customer;

import com.example.pgr209exam.controller.CustomerController;
import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.model.Customer;
import com.example.pgr209exam.repository.AddressRepository;
import com.example.pgr209exam.repository.CustomerRepository;
import com.example.pgr209exam.service.CustomerService;
import com.example.pgr209exam.wrapper.CustomerAddressWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class CustomerServiceTests {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private CustomerService customerService;

    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        customerController = new CustomerController(customerService);
    }

    @Test
    void testGetCustomers() {
        Page<Customer> mockedPage = mock(Page.class);
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(mockedPage);
        Page<Customer> result = customerService.getCustomers(PageRequest.of(0, 5));
        verify(customerRepository, times(1)).findAll(any(Pageable.class));
        assertEquals(mockedPage, result);
    }

    @Test
    void testGetCustomerById() {
        long testCustomerId = 123;
        Customer expectedCustomer = new Customer();
        expectedCustomer.setCustomerId(testCustomerId);
        expectedCustomer.setCustomerName("test1");
        expectedCustomer.setCustomerEmail("test@email.com");

        when(customerRepository.findById(testCustomerId)).thenReturn(Optional.of(expectedCustomer));
        Customer response = customerService.getCustomerById(testCustomerId);
        assertEquals(expectedCustomer, response);
    }

    @Test
    void testCreateCustomer() {
        Customer inputCustomer = new Customer();
        inputCustomer.setCustomerId(null);
        inputCustomer.setCustomerName("test1");
        inputCustomer.setCustomerEmail("test@email.com");

        when(customerService.createCustomer(any())).thenReturn(inputCustomer);
        Customer response = customerController.createCustomer(inputCustomer);

        assertNotNull(response);
        assertEquals(inputCustomer.getCustomerName(), response.getCustomerName());
        assertEquals(inputCustomer.getCustomerEmail(), response.getCustomerEmail());
    }

    @Test
    void testUpdateCustomer(){
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustomerName("test1");
        customer.setCustomerEmail("test1@email.com");

        Customer customer2 = new Customer();
        customer2.setCustomerId(customerId);
        customer2.setCustomerName("test2");
        customer2.setCustomerEmail("test2@email.com");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer2);

        Customer result = customerService.updateCustomer(customer2);

        assertNotNull(result);
        assertEquals(customer2.getCustomerName(), result.getCustomerName());
        assertEquals(customer2.getCustomerEmail(), result.getCustomerEmail());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testDeleteCustomerById() {
        long testCustomerId = 123;
        doNothing().when(customerRepository).deleteById(testCustomerId);
        customerService.deleteCustomerById(testCustomerId);
        verify(customerRepository, times(1)).deleteById(testCustomerId);
    }

    @Test
    void testDeleteCustomerByIdException() {
        long testCustomerId = 111111;
        String message = "Customer not found";
        doThrow(new NoSuchElementException(message)).when(customerRepository).deleteById(testCustomerId);

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            customerService.deleteCustomerById(testCustomerId);
        });

        assertEquals(message, exception.getMessage());
        verify(customerRepository, times(1)).deleteById(testCustomerId);

    }

    @Test
    void testAddAddressToCustomer(){
        Long customerId = 1L;
        Customer mockCustomer = new Customer();
        Address address = new Address();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(mockCustomer);
        Customer updatedCustomer = customerService.addAddressToCustomer(customerId, address);

        assertNotNull(updatedCustomer);
        assertTrue(updatedCustomer.getAddresses().contains(address));
        verify(customerRepository, times(1)).save(mockCustomer);
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testCreateCustomerWithAddress(){
        Customer customer = new Customer();
        Address address = new Address();
        address.setAddressId(1L);
        CustomerAddressWrapper wrapper = new CustomerAddressWrapper(customer, Arrays.asList(address));

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(addressRepository.findById(anyLong())).thenReturn(java.util.Optional.of(address));

        Customer result = customerService.createCustomerWithAddress(wrapper);

        assertNotNull(result);
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(addressRepository, times(1)).findById(anyLong());
    }
}