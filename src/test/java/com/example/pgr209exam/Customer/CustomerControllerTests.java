package com.example.pgr209exam.Customer;

import com.example.pgr209exam.model.Customer;
import com.example.pgr209exam.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testGetCustomers() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(customerService, times(1)).getCustomers(any());
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Long customerId = 1L;
        when(customerService.getCustomerById(customerId)).thenReturn(new Customer());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(customerService, times(1)).getCustomerById(customerId);
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customer")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
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
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(customerService, times(1)).updateCustomer(any(Customer.class));
    }


    @Test
    public void testDeleteCustomerById() throws Exception {
        long customerId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customer/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(customerService, times(1)).deleteCustomerById(customerId);
    }
}

