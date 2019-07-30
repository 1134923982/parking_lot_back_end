package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.enums.OrdersStatusEnum;
import com.oocl.ita.parkinglot.enums.ParkingLotStatusEnum;
import com.oocl.ita.parkinglot.exception.ParkingLotException;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.service.EmployeeService;
import com.oocl.ita.parkinglot.vo.PageVO;
import com.oocl.ita.parkinglot.vo.ParkingLotVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
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
        PageVO<ParkingLot> employeeAllParkingLots = employeeService.getEmployeeAllParkingLots(employee.getId());

        assertEquals(employeeAllParkingLots.getPageContent().size(),2);

    }

    @Test(expected = ParkingLotException.class)
    public void should_return_null_when_employee_is_not_exist() {
        Employee employee = null;

        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.ofNullable(employee));

        PageVO<ParkingLot> employeeAllParkingLots = employeeService.getEmployeeAllParkingLots("0");
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
    public void should_return_all_parking_lots_with_parking_boy_by_manager_when_get_parking_lot_by_manager_id_and_page_size(){
        Employee manager = new Employee();
        manager.setId("1");
        Employee parkingBoy1 = new Employee();
        Employee parkingBoy2 = new Employee();
        List<Employee> employees = new ArrayList<>();
        employees.add(parkingBoy1);
        employees.add(parkingBoy2);
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot("1","a","aa",10,10,1 ));
        parkingLots.add(new ParkingLot("2","a","aa",10,10,1 ));
        manager.setParkingLots(parkingLots);

        when(employeeRepository.findEmployeesByParkingLotsContains(Mockito.any())).thenReturn(employees);
        when(employeeRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(manager));

        List<ParkingLotVO> parkingLotVOsByEmployeeId = employeeService.getParkingLotVOsByEmployeeId("1", 1, 3);
        assertEquals(2, parkingLotVOsByEmployeeId.size());
    }

    @Test
    public void should_return_null_array_by_manager_when_get_parking_lot_page_and_page_size_is_un_vaild(){
        Employee manager = new Employee();
        manager.setId("1");
        Employee parkingBoy1 = new Employee();
        Employee parkingBoy2 = new Employee();
        List<Employee> employees = new ArrayList<>();
        employees.add(parkingBoy1);
        employees.add(parkingBoy2);
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot("1","a","aa",10,10,1 ));
        parkingLots.add(new ParkingLot("2","a","aa",10,10,1 ));
        manager.setParkingLots(parkingLots);

        when(employeeRepository.findEmployeesByParkingLotsContains(Mockito.any())).thenReturn(employees);
        when(employeeRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(manager));

        List<ParkingLotVO> parkingLotVOsByEmployeeId = employeeService.getParkingLotVOsByEmployeeId("1", 3, 3);
        assertEquals(0, parkingLotVOsByEmployeeId.size());
    }

    @Test
    public void should_return_null_when_given_not_exists_manager_id() {
        when(employeeRepository.findByManagedId(anyString())).thenReturn(null);

        assertNull(employeeService.getParkingBoyByManagedId("not exists"));
    }

    @Test
    public void should_return_employeeVO_when_given_manager_id_whose_had_only_one_parking_boy() {
        Employee employee = new Employee("name", "idCard", "gender", "phone", 0, "managedId");

        when(employeeRepository.findByManagedId(anyString())).thenReturn(Arrays.asList(employee));

        assertEquals(1, employeeService.getParkingBoyByManagedId("only contain one parking boy").size());
    }

    @Test
    public void should_return_employeeVO_when_given_manager_id_whose_had_more_than_one_parking_boy() {
        Employee employee = new Employee("name", "idCard", "gender", "phone", 0, "managedId");
        Employee employee1 = new Employee("2name", "idCard", "gender", "phone", 0, "managedId");

        when(employeeRepository.findByManagedId(anyString())).thenReturn(Arrays.asList(employee, employee1));

        assertEquals(2, employeeService.getParkingBoyByManagedId("contain more than one parking boy").size());
    }

    @Test
    public void should_return_employeeVO_when_given_manager_id_whose_had_no_parking_boy() {
        when(employeeRepository.findByManagedId(anyString())).thenReturn(null);

        assertNull( employeeService.getParkingBoyByManagedId("had no parking boy"));
    }
}