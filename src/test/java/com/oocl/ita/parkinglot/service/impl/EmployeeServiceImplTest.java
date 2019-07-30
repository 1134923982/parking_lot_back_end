package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.enums.CodeMsgEnum;
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

        assertEquals(employeeAllParkingLots.getPageContent().size(), 2);

    }

    @Test(expected = ParkingLotException.class)
    public void should_return_null_when_employee_is_not_exist() {
        Employee employee = null;

        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.ofNullable(employee));

        PageVO<ParkingLot> employeeAllParkingLots = employeeService.getEmployeeAllParkingLots("0");
    }

    @Test
    public void should_return_employee_by_id() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setTelephone("13587671359");
        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));
        Employee reEmployee = employeeService.getEmployeeById("1");
        assertEquals(reEmployee.getTelephone(), employee.getTelephone());
    }

    @Test
    public void should_get_employee_unfinish_orders() {
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
        when(ordersRepository.findEmployeeFinishOrders(anyString())).thenReturn(orders);

        List<Orders> findOrders = employeeService.getEmployeeOrdersByFinish(employee.getId(), false);
        assertEquals(findOrders.size(), 2);
    }

    @Test
    public void should_get_employee_finish_orders() {
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
        assertEquals(findOrders.size(), 3);
    }

    @Test
    public void should_return_all_parking_lots_with_parking_boy_by_manager_when_get_parking_lot_by_manager_id_and_page_size() {
        Employee manager = new Employee();
        manager.setId("1");
        Employee parkingBoy1 = new Employee();
        Employee parkingBoy2 = new Employee();
        List<Employee> employees = new ArrayList<>();
        employees.add(parkingBoy1);
        employees.add(parkingBoy2);
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot("1", "a", "aa", 10, 10, 1));
        parkingLots.add(new ParkingLot("2", "a", "aa", 10, 10, 1));
        manager.setParkingLots(parkingLots);

        when(employeeRepository.findEmployeesByParkingLotsContains(Mockito.any())).thenReturn(employees);
        when(employeeRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(manager));

        List<ParkingLotVO> parkingLotVOsByEmployeeId = employeeService.getParkingLotVOsByEmployeeId("1", 1, 3);
        assertEquals(2, parkingLotVOsByEmployeeId.size());
    }

    @Test
    public void should_return_null_array_by_manager_when_get_parking_lot_page_and_page_size_is_un_vaild() {
        Employee manager = new Employee();
        manager.setId("1");
        Employee parkingBoy1 = new Employee();
        Employee parkingBoy2 = new Employee();
        List<Employee> employees = new ArrayList<>();
        employees.add(parkingBoy1);
        employees.add(parkingBoy2);
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot("1", "a", "aa", 10, 10, 1));
        parkingLots.add(new ParkingLot("2", "a", "aa", 10, 10, 1));
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

    @Test
    public void should_return_the_employee_list_when_find_all() {
        List employeeList = new ArrayList();
        employeeList.add(new Employee());
        when(employeeRepository.findAll()).thenReturn(employeeList);

        List<Employee> resultList = employeeService.findAllEmployees(-1);
        assertEquals(employeeList.size(), resultList.size());
    }

    @Test
    public void should_return_employee_when_create_employee() {
        Employee employee = new Employee();
        employee.setName("zhangsan");
        when(employeeRepository.save(employee)).thenReturn(employee);
        assertEquals(employee, employeeService.createEmployee(employee));
    }

    @Test
    public void should_return_all_orders_by_manager_id_when_get_orders_by_manager_id(){
        Employee manager = new Employee();
        manager.setId("1");
        manager.setRole(1);
        List<Orders> orders = new ArrayList<>();
        orders.add(new Orders());
        orders.add(new Orders());
        
        when(ordersRepository.findParkingCarOrdersByManagerId(anyString())).thenReturn(orders);
        when(ordersRepository.findFetchingCarOrdersByManagerId(anyString())).thenReturn(orders);
        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(manager));

        List<Orders> employeeOrdersByFinish = employeeService.getEmployeeOrdersByFinish("1", true);
        assertEquals(4, employeeOrdersByFinish.size());

    }

    @Test
    public void should_return_employees_whose_role_is_0_when_role_is_0() {
        List<Employee> employeesList = new ArrayList<>();
        employeesList.add(new Employee());
        employeesList.add(new Employee());
        when(employeeRepository.findByRole(anyInt())).thenReturn(employeesList);
        List<Employee> employees = employeeService.findAllEmployees(0);
        assertEquals(2, employees.size());
    }

    @Test(expected = ParkingLotException.class)
    public void should_throw_exception_when_employee_id_is_not_exists() {
        when(employeeRepository.findById(anyString()).orElseThrow(() -> new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR))).thenThrow(ParkingLotException.class);

        employeeService.updateEmployee(new Employee());
    }

    @Test
    public void should_return_update_employee_when_given_telephone() {
        Employee employee = new Employee();
        employee.setTelephone("111");

        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(new Employee()));

        assertEquals("111", employeeService.updateEmployee(employee).getTelephone());
    }

    @Test
    public void should_return_update_employee_when_given_status() {
        Employee employee = new Employee();
        employee.setStatus(1);

        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(new Employee()));

        assertEquals(1, employeeService.updateEmployee(employee).getStatus());
    }

    @Test
    public void should_return_not_update_employee_when_given_status_in_special_values() {
        Employee employee = new Employee();
        employee.setStatus(-1);

        Employee secondEmployee = new Employee();
        secondEmployee.setStatus(1);
        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(secondEmployee));

        assertEquals(1, employeeService.updateEmployee(employee).getStatus());
    }

    @Test
    public void should_return_update_employee_when_given_parkingLots() {
        Employee employee = new Employee();
        employee.setParkingLots(Arrays.asList(new ParkingLot(), new ParkingLot()));

        Employee secondEmployee = new Employee();
        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));

        assertEquals(2, employeeService.updateEmployee(employee).getParkingLots().size());
    }

    @Test
    public void should_return_not_update_employee_when_not_given_parkingLots() {
        Employee employee = new Employee();
        Employee secondEmployee = new Employee();

        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));

        assertNull(employeeService.updateEmployee(employee).getParkingLots());
    }

    @Test
    public void should_return_update_employee_when_given_telephone_and_parkingLots_and_status() {
        Employee employee = new Employee();
        employee.setParkingLots(Arrays.asList(new ParkingLot(), new ParkingLot()));
        employee.setStatus(1);
        employee.setTelephone("123");

        Employee secondEmployee = new Employee();

        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));

        assertEquals(2, employeeService.updateEmployee(employee).getParkingLots().size());
        assertEquals(1, employeeService.updateEmployee(employee).getStatus());
        assertEquals("123", employeeService.updateEmployee(employee).getTelephone());
    }
}