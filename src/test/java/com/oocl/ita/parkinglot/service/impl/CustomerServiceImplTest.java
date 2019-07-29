package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceImplTest {
    @MockBean
    private OrdersRepository ordersRepository;
    @Autowired
    private CustomerService customerService;
    @Test
    public void getCustomerHistoryOrdersByCustomerId() throws Exception {
        Customer customer = new Customer();
        customer.setId("1");
        Orders orders = new Orders();
        orders.setCustomer(customer);
        orders.setStatus(6);
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(orders);
        when(ordersRepository.findByCustomer_IdAndStatusIsFinish(anyString()))
                .thenReturn(ordersList);
        List<Orders> customerHistoryOrdersByCustomerId = customerService.getCustomerHistoryOrdersByCustomerId(customer.getId());
        assertEquals(customerHistoryOrdersByCustomerId.size(),ordersList.size());
    }

    @Test
    public void getCustomerProcessingOrdersByCustomerId() throws Exception {
        Customer customer = new Customer();
        customer.setId("1");
        Orders orders = new Orders();
        orders.setCustomer(customer);
        orders.setStatus(2);
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(orders);
        when(ordersRepository.findByCustomer_IdAndStatusIsUnFinish(anyString()))
                .thenReturn(ordersList);
        List<Orders> customerHistoryOrdersByCustomerId = customerService.getCustomerProcessingOrdersByCustomerId(customer.getId());
        assertEquals(customerHistoryOrdersByCustomerId.size(),ordersList.size());
    }

}