package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.repository.ParkingLotRepository;
import com.oocl.ita.parkinglot.service.OrdersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrdersServiceImplTest {

    @MockBean
    private OrdersRepository ordersRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private OrdersService ordersService;

    @Test
    public void should_return_updated_order_parking_boy_accept_park_order() {
        Orders orders = new Orders();
        orders.setOrderId("123");

        Customer customer = new Customer();
        customer.setUserName("test");
        orders.setCustomer(customer);

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId("456");
        Employee employee = new Employee();
        employee.setId("789");
        employee.setName("test");

        when(ordersRepository.findById(anyString())).thenReturn(java.util.Optional.of(orders));
        when(parkingLotRepository.findById(anyString())).thenReturn(java.util.Optional.of(parkingLot));
        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));

        assertEquals("test", ordersService.updateOrders("123", "456", "789").getCustomerName());
    }

    @Test
    public void should_return_updated_order_parking_boy_update_order_status() {
        Orders orders = new Orders();
        orders.setOrderId("123");
        orders.setStatus(1);

        Customer customer = new Customer();
        customer.setUserName("test");
        orders.setCustomer(customer);

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId("456");
        orders.setParkingLot(parkingLot);

        Employee employee = new Employee();
        employee.setId("789");
        employee.setName("test");

        when(ordersRepository.findById(anyString())).thenReturn(java.util.Optional.of(orders));
        when(parkingLotRepository.findById(anyString())).thenReturn(java.util.Optional.of(parkingLot));
        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));

        assertEquals(2, ordersService.updateOrders("123", null, null).getStatus());
    }
}