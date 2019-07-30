package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.exception.ParkingLotException;
import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.repository.CustomerRepository;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.service.CustomerService;
import org.junit.Ignore;
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
    @MockBean
    private CustomerRepository customerRepository;
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

    @Test
    public void should_return_a_new_orders_when_input_a_orders_and_has_no_unfinish_orders(){
        Customer customer = new Customer();
        Orders orders = new Orders();
        orders.setStatus(0);
        orders.setCustomer(customer);
        List<Orders> list = new ArrayList<>();
        when(ordersRepository.findByCustomer_IdAndStatusIsUnFinish(anyString()))
                .thenReturn(list);
        when(customerRepository.findById(anyString()))
                .thenReturn(java.util.Optional.ofNullable(customer));
        when(ordersRepository.save(orders))
                .thenReturn(orders);
        assertEquals(customerService.createCustomerOrders("1",orders).getStatus(),orders.getStatus());
    }

    @Test(expected= ParkingLotException.class)
    public void should_return_a_new_orders_when_input_a_orders_and_has_unfinish_orders(){
        Customer customer = new Customer();
        customer.setId("1");
        Orders orders = new Orders();
        orders.setStatus(4);
        orders.setCustomer(customer);
        List<Orders> list = new ArrayList<>();
        list.add(orders);
        when(ordersRepository.findByCustomer_IdAndStatusIsUnFinish(anyString()))
                .thenReturn(list);
        when(ordersRepository.save(orders))
                .thenReturn(orders);
        assertEquals(customerService.createCustomerOrders("1",orders).getStatus(),orders.getStatus());
    }

}