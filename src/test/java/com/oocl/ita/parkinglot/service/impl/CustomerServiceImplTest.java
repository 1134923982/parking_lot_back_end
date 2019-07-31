package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.enums.CodeMsgEnum;
import com.oocl.ita.parkinglot.enums.OrdersStatusEnum;
import com.oocl.ita.parkinglot.exception.ParkingLotException;
import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.repository.CustomerRepository;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.service.CustomerService;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

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
        when(ordersRepository.findByCustomerIdAndStatusIsFinish(anyString()))
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
        when(ordersRepository.findByCustomerIdAndStatusIsUnFinish(anyString()))
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
        when(ordersRepository.findByCustomerIdAndStatusIsUnFinish(anyString()))
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
        when(ordersRepository.findByCustomerIdAndStatusIsUnFinish(anyString()))
                .thenReturn(list);
        when(ordersRepository.save(orders))
                .thenReturn(orders);
        assertEquals(customerService.createCustomerOrders("1",orders).getStatus(),orders.getStatus());
    }

    @Test(expected = ParkingLotException.class)
    public void should_throw_exception_when_customer_not_exists() {
        when(customerRepository.findById(anyString()).orElseThrow(() -> new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR))).thenThrow(ParkingLotException.class);

        customerService.doesTheCustomerOwnTheOrder("not exists", "any");
    }

    @Test(expected = ParkingLotException.class)
    public void should_throw_exception_when_order_not_exists() {
        Customer customer = new Customer();

        when(customerRepository.findById(anyString())).thenReturn(java.util.Optional.of(customer));
        when(ordersRepository.findById(anyString()).orElseThrow(() -> new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR))).thenThrow(ParkingLotException.class);

        customerService.doesTheCustomerOwnTheOrder("exists", "not exists");
    }

    @Test
    public void should_return_false_when_order_owner_is_not_the_customer() {
        Customer customer = new Customer();
        Orders orders = new Orders();
        orders.setCustomer(customer);

        when(customerRepository.findById(anyString())).thenReturn(java.util.Optional.of(new Customer()));
        when(ordersRepository.findById(anyString())).thenReturn(java.util.Optional.of(orders));

        assertFalse(customerService.doesTheCustomerOwnTheOrder("exists", "exists"));
    }

    @Test
    public void should_return_true_when_order_owner_is_the_customer() {
        Customer customer = new Customer();
        customer.setId("exists");

        Orders orders = new Orders();
        orders.setCustomer(customer);

        when(customerRepository.findById(anyString())).thenReturn(java.util.Optional.of(customer));
        when(ordersRepository.findById(anyString())).thenReturn(java.util.Optional.of(orders));

        assertTrue(customerService.doesTheCustomerOwnTheOrder("exists", "exists"));
    }

    @Test
    public void should_return_false_when_status_is_not_valid() {
        int status = OrdersStatusEnum.PARK_ORDER_NOT_RECEIVED.ordinal();
        Orders orders = new Orders();
        orders.setStatus(OrdersStatusEnum.PARK_ORDER_RECEIVED.ordinal());

        assertFalse(customerService.isValidWaitingForUpdateStatus(orders, status));
    }

    @Test
    public void should_return_true_when_status_is_not_valid() {
        int status = OrdersStatusEnum.FETCH_ORDER_CUSTOMER_CONFIRMED.ordinal();
        Orders orders = new Orders();
        orders.setStatus(OrdersStatusEnum.FETCH_ORDER_COMPLETED.ordinal());

        assertTrue(customerService.isValidWaitingForUpdateStatus(orders, status));
    }


    @Test(expected = ParkingLotException.class)
    public void should_throw_exception_when_wait_for_update_status_is_not_valid() {
        Customer customer = new Customer();
        Orders orders = new Orders();
        orders.setCustomer(customer);
        orders.setStatus(OrdersStatusEnum.PARK_ORDER_NOT_RECEIVED.ordinal());

        when(customerRepository.findById(anyString())).thenReturn(java.util.Optional.of(customer));
        when(ordersRepository.findById(anyString())).thenReturn(java.util.Optional.of(orders));
        when(ordersRepository.getOne(anyString())).thenReturn(orders);

        customerService.updateOrdersStatus("exists", "exists", -1);
    }

    @Test(expected = ParkingLotException.class)
    public void should_throw_exception_when_customer_not_own_the_order() {
        Customer customer = new Customer();
        Orders orders = new Orders();
        orders.setCustomer(customer);

        when(customerRepository.findById(anyString())).thenReturn(java.util.Optional.of(new Customer()));
        when(ordersRepository.findById(anyString())).thenReturn(java.util.Optional.of(orders));
        when(ordersRepository.getOne(anyString())).thenReturn(orders);

        customerService.updateOrdersStatus("exists", "exists", -1);
    }

    @Test
    public void should_return_updated_order_when_customer_and_order_is_in_valid_condition() {
        Customer customer = new Customer();
        customer.setId("is me");
        Orders orders = new Orders();
        orders.setCustomer(customer);
        orders.setStatus(OrdersStatusEnum.FETCH_ORDER_COMPLETED.ordinal());

        when(customerRepository.findById(anyString())).thenReturn(java.util.Optional.of(customer));
        when(ordersRepository.findById(anyString())).thenReturn(java.util.Optional.of(orders));
        when(ordersRepository.getOne(anyString())).thenReturn(orders);

        int expectStatus = OrdersStatusEnum.FETCH_ORDER_CUSTOMER_CONFIRMED.ordinal();
        int actualStatus = customerService.updateOrdersStatus("is me", "any", OrdersStatusEnum.FETCH_ORDER_CUSTOMER_CONFIRMED.ordinal()).getStatus();
        assertEquals(expectStatus, actualStatus);
    }
}