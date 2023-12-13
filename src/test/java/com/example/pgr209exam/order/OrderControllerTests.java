package com.example.pgr209exam.order;
import com.example.pgr209exam.controller.OrderController;
import com.example.pgr209exam.model.Order;
import com.example.pgr209exam.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderControllerTests {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;
    @InjectMocks
    private OrderController orderController;

    @Test
    public void testGetOrders() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(orderService, times(1)).getOrders(any());
    }

    @Test
    public void testGetOrdersById() throws Exception{
        Long orderId = 1L;
        when(orderService.getOrderById(orderId)).thenReturn(new Order());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    public void testCreateOrder() throws Exception {
        Order order = new Order();
        order.setOrderId(1L);
        when(orderService.createOrder(any(Order.class))).thenReturn(order);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(orderService, times(1)).createOrder(any(Order.class));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        Order order = new Order();
        order.setOrderId(1L);
        when(orderService.updateOrder(any(Order.class))).thenReturn(order);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/order")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(orderService, times(1)).updateOrder(any(Order.class));
    }

    @Test
    public void testDeleteOrderById() throws Exception {
        long orderId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/order/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(orderService, times(1)).deleteOrderById(orderId);
    }

}
