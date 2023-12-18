package com.example.pgr209exam.order;

import com.example.pgr209exam.model.Customer;
import com.example.pgr209exam.model.Order;
import com.example.pgr209exam.repository.OrderRepository;
import com.example.pgr209exam.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@SpringBootApplication(scanBasePackages = "com.example.pgr209exam")
public class OrderServiceTests {
    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void testGetOrders(){
        Page<Order> mockedPage = mock(Page.class);
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(mockedPage);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> result = orderService.getOrders(pageable);

        verify(orderRepository, times(1)).findAll(pageable);
        assertEquals(mockedPage, result);
    }

    @Test
    public void testGetOrderById(){
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(new Order()));
        Order result = orderService.getOrderById(1L);

        verify(orderRepository, times(1)).findById(1L);
        assertEquals(Order.class, result.getClass());
    }

    @Test
    public void testCreateOrder(){
        Order orderTest = new Order();
        orderTest.setOrderId(1L);
        when(orderRepository.save(orderTest)).thenReturn(orderTest);
        Order createdOrder = orderService.createOrder(orderTest);

        verify(orderRepository, times(1)).save(orderTest);
        assertEquals(orderTest, createdOrder);
    }

    @Test
    public void testUpdateOrder(){
        Customer customer1 = new Customer();
        customer1.setCustomerId(1L);
        customer1.setCustomerName("testOld customer");

        Customer customer2 = new Customer();
        customer2.setCustomerId(2L);
        customer2.setCustomerName("test new customer");

        Order exsistingOrder = new Order();
        exsistingOrder.setOrderId(1L);

        Order testUpdateOrder = new Order();
        testUpdateOrder.setOrderId(1L);
        testUpdateOrder.setCustomer(customer1);

        when(orderRepository.save(testUpdateOrder)).thenReturn(testUpdateOrder);
        Order result = orderService.updateOrder(testUpdateOrder);

        verify(orderRepository, times(1)).save(testUpdateOrder);
        assertEquals(testUpdateOrder, result);
    }
    @Test
    public void testDeleteOrderById(){
        Order existingOrder = new Order();
        existingOrder.setOrderId(1L);
        when(orderRepository.findById(existingOrder.getOrderId())).thenReturn(Optional.of(existingOrder));
        orderService.deleteOrderById(existingOrder.getOrderId());

        verify(orderRepository, times(1)).deleteById(existingOrder.getOrderId());
    }
}
