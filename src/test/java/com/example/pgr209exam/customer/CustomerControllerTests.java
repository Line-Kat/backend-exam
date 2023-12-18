package com.example.pgr209exam.customer;

import com.example.pgr209exam.controller.CustomerController;
import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.model.Customer;
import com.example.pgr209exam.model.Machine;
import com.example.pgr209exam.model.Order;
import com.example.pgr209exam.service.CustomerService;
import com.example.pgr209exam.wrapper.CustomerAddressWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetCustomers() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService, times(1)).getCustomers(any());
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Long customerId = 1L;
        when(customerService.getCustomerById(customerId)).thenReturn(new Customer());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService, times(1)).getCustomerById(customerId);
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);
        mockMvc.perform(post("/api/customer")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    public void testUpdateCustomer() throws Exception {

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        when(customerService.updateCustomer(any(Customer.class))).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/customer")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService, times(1)).updateCustomer(any(Customer.class));
    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        long customerId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customer/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService, times(1)).deleteCustomerById(customerId);
    }

    @Test
    public void addAddressToCustomer_newAddressIsAddedToCustomer_shouldReturnNewAddress() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        when(customerService.updateCustomer(any(Customer.class))).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/customer/1/address")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService, times(1)).addAddressToCustomer(anyLong(), any(Address.class));
    }

    @Test
    public void addOrder_newOrderIsAddedToCustomer() throws Exception {

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        when(customerService.updateCustomer(any(Customer.class))).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/customer/1/order")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService, times(1)).addOrder(anyLong(), any(Order.class));
    }

    @Test
    public void createCustomerWithAddress() throws Exception {
        Long CustomerId = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(CustomerId);
        customer.setCustomerName("Test name");
        customer.setCustomerEmail("Test@email.com");

        Address address = new Address();
        address.setAddressId(2L);
        address.setAddressName("test address");

        CustomerAddressWrapper wrapper = new CustomerAddressWrapper(customer, List.of(address));

        given(customerService.createCustomerWithAddress(any(CustomerAddressWrapper.class))).willReturn(customer);

        mockMvc.perform(post("/api/customer/createWithAddress") // Corrected URL
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrapper)))
                .andExpect(status().isOk());

        verify(customerService, times(1)).createCustomerWithAddress(any(CustomerAddressWrapper.class));
    }
}

