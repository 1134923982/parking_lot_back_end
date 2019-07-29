package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.enums.OrdersStatusEnum;
import com.oocl.ita.parkinglot.enums.ParkingLotStatusEnum;
import com.oocl.ita.parkinglot.exception.ParkingLotException;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.repository.ParkingLotRepository;
import com.oocl.ita.parkinglot.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceImplTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private OrdersRepository ordersRepository;

    @MockBean
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private EmployeeService employeeService;


    @Test
    public void should_get_employee_all_parkinglots_when_employee_is_exist() {
        ParkingLot firstParkingLot = new ParkingLot();
        firstParkingLot.setStatus(ParkingLotStatusEnum.EXIST.ordinal());
        ParkingLot secondParkingLot = new ParkingLot();
        secondParkingLot.setStatus(ParkingLotStatusEnum.EXIST.ordinal());
        ParkingLot thirdParkingLot = new ParkingLot();
        thirdParkingLot.setStatus(ParkingLotStatusEnum.DELETE.ordinal());
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);
        parkingLots.add(thirdParkingLot);
        Employee employee = new Employee();
        employee.setParkingLots(parkingLots);

        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));
        List<ParkingLot> findParkingLots = employeeService.getEmployeeAllParkingLots(employee.getId());

        assertEquals(2,findParkingLots.size());

    }

    @Test
    public void should_return_null_when_employee_is_not_exist() {
        Employee employee = null;

        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.ofNullable(employee));

        List<ParkingLot> findParkingLots = employeeService.getEmployeeAllParkingLots("0");
        assertNull(findParkingLots);
    }

    @Test
    public void should_return_employee_by_id(){
        Employee employee = new Employee();
        employee.setId("1");
        employee.setTelephone("13587671359");
        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));
        Employee reEmployee = employeeService.getEmployeeById("1");
        assertEquals(reEmployee.getTelephone(),employee.getTelephone());
    }

    @Test
    public void should_get_employee_unfinish_orders(){
        Employee employee = new Employee();
        Orders firstOrder = new Orders();
        firstOrder.setParkingBoy(employee);
        firstOrder.setStatus(OrdersStatusEnum.PARK_ORDER_RECEIVED.ordinal());
        Orders secondOrder = new Orders();
        secondOrder.setParkingBoy(employee);
        secondOrder.setStatus(OrdersStatusEnum.FETCH_ORDER_PICKED_UP_THE_CAR.ordinal());
        ArrayList<Orders> orders = new ArrayList<>();
        orders.add(firstOrder);
        orders.add(secondOrder);

        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));
        when(ordersRepository.findEmployeeUnfinishOrders(anyString())).thenReturn(orders);

        List<Orders> findOrders = employeeService.getEmployeeOrdersByFinish(employee.getId(), false);
        assertEquals(findOrders.size(),2);
    }

    @Test
    public void should_get_employee_finish_orders(){
        Employee employee = new Employee();
        Orders firstOrder = new Orders();
        firstOrder.setParkingBoy(employee);
        firstOrder.setStatus(OrdersStatusEnum.PARK_ORDER_RECEIVED.ordinal());
        Orders secondOrder = new Orders();
        secondOrder.setParkingBoy(employee);
        secondOrder.setStatus(OrdersStatusEnum.FETCH_ORDER_PICKED_UP_THE_CAR.ordinal());
        ArrayList<Orders> firstOrders = new ArrayList<>();
        firstOrders.add(firstOrder);
        firstOrders.add(secondOrder);
        ArrayList<Orders> secondOrders = new ArrayList<>();
        secondOrders.add(firstOrder);

        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));
        when(ordersRepository.findEmployeeParkingFinishOrders(anyString())).thenReturn(firstOrders);
        when(ordersRepository.findEmployeeFetchingFinishOrders(anyString())).thenReturn(secondOrders);

        List<Orders> findOrders = employeeService.getEmployeeOrdersByFinish(employee.getId(), true);
        assertEquals(findOrders.size(),3);
    }

    @Test
    public void should_return_1_when_update_employee_parkinglot_capacity_success(){
        ParkingLot parkingLot = new ParkingLot();
        Employee employee = new Employee();
        employee.setId("1");
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        employee.setParkingLots(parkingLots);
        when(parkingLotRepository.updateCapacityById(anyString(),anyInt())).thenReturn(1);
        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));
        int result = employeeService.updateEmployeeParkingLotCapacityById(employee.getId(), parkingLot);
        assertEquals(result,1);
    }

    @Test(expected = ParkingLotException.class)
    public void should_throw_ParkingLotExcepotion_when_update_employee_parkinglot_capacity_fail(){
        ParkingLot parkingLot = new ParkingLot();

        when(parkingLotRepository.updateCapacityById(anyString(),anyInt())).thenReturn(0);

        int result = employeeService.updateEmployeeParkingLotCapacityById("1", parkingLot);
        assertEquals(result,0);
    }

    @Test
    public void should_add_employee_new_parking_lot(){
        ParkingLot parkingLot = new ParkingLot();
        Employee employee = new Employee();
        employee.setId("1");
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        employee.setParkingLots(parkingLots);

        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(parkingLot);
        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        employeeService.addEmployeeNewParkingLot(employee.getId(), parkingLot);
        boolean result = employee.getParkingLots().contains(parkingLot);
        assertEquals(true,result);
    }

    @Test(expected = ParkingLotException.class)
    public void should_throws_Exception_when_add_new_employee_parking_lot_and_employee_is_not_exist(){
        Employee employee = null;
        ParkingLot parkingLot = new ParkingLot();

        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.ofNullable(employee));

        employeeService.addEmployeeNewParkingLot("1", parkingLot);

    }




}