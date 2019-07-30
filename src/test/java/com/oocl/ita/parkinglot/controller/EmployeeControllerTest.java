package com.oocl.ita.parkinglot.controller;

import com.google.gson.Gson;
import com.oocl.ita.parkinglot.enums.ParkingLotStatusEnum;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.service.EmployeeService;

import com.oocl.ita.parkinglot.utils.SecurityUtils;

import com.oocl.ita.parkinglot.vo.PageVO;
import com.oocl.ita.parkinglot.vo.ParkingLotVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_employee_all_parkinglots_when_employee_is_exist () throws Exception{
        ParkingLot firstParkingLot = new ParkingLot();
        firstParkingLot.setStatus(ParkingLotStatusEnum.EXIST.ordinal());
        ParkingLot secondParkingLot = new ParkingLot();
        secondParkingLot.setStatus(ParkingLotStatusEnum.EXIST.ordinal());
        ParkingLot thirdParkingLot = new ParkingLot();
        thirdParkingLot.setStatus(ParkingLotStatusEnum.EXIST.ordinal());
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);
        parkingLots.add(thirdParkingLot);
        PageVO<ParkingLot> parkingLotPageVO = new PageVO<>();
        parkingLotPageVO.setPageContent(parkingLots);

        when(employeeService.getEmployeeAllParkingLots(anyString())).thenReturn(parkingLotPageVO);

        mockMvc.perform(get("/employees/{id}/parking-lots","1")
                .header("token", SecurityUtils.getTestToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.pageContent.length()").value(3));
    }

   
    @Test
    public void should_return_employee_when_request_by_employee_id() throws Exception {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setTelephone("13867549756");
        when(employeeService.getEmployeeById(anyString())).thenReturn(employee);
        mockMvc.perform(get("/employees/{id}","1")
                .header("token",SecurityUtils.getTestToken()))
                .andDo(print())
                .andExpect(jsonPath("$.data.telephone").value(employee.getTelephone()));
    }

    @Test
    public void should_return_employee_All_unfinish_orders_when_employee_is_exist() throws Exception {
        Employee parkingBoy = new Employee();
        parkingBoy.setRole(1);
        Orders firstOrder = new Orders();
        firstOrder.setParkingBoy(parkingBoy);
        firstOrder.setStatus(1);
        Orders secondOrder = new Orders();
        secondOrder.setParkingBoy(parkingBoy);
        secondOrder.setStatus(2);
        Orders thirdOrder = new Orders();
        thirdOrder.setFetchingBoy(parkingBoy);
        thirdOrder.setStatus(3);
        ArrayList<Orders> orders = new ArrayList<>();
        orders.add(firstOrder);
        orders.add(secondOrder);
        orders.add(thirdOrder);

        when(employeeService.getEmployeeOrdersByFinish(anyString(),anyBoolean())).thenReturn(orders);

        mockMvc.perform(get("/employees/{id}/orders","1").param("finish","false")
                .header("token",SecurityUtils.getTestToken()))
                .andDo(print())
                .andExpect(jsonPath("$.data.length()").value(3))
                .andExpect(jsonPath("$.data[0].status").value(1));
    }

    @Test
    public void should_return_parking_lots_with_parking_boys_when_manager_get_all_parking_lots_with_status() throws Exception {

        List<ParkingLotVO> parkingLotVOS = new ArrayList<>();
        parkingLotVOS.add(new ParkingLotVO());
        parkingLotVOS.add(new ParkingLotVO());

        when(employeeService.getParkingLotVOsByEmployeeId(anyString(), anyInt(), anyInt())).thenReturn(parkingLotVOS);

        mockMvc.perform(get("/employees/2/parking-lots/1?page=1&pageSize=1")
                .header("token",SecurityUtils.getTestToken()))
                .andDo(print())
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    public void should_return_all_employees_when_request_for_all() throws Exception {

        List<Employee> employees = new ArrayList();
        employees.add(new Employee());
        employees.add(new Employee());
        when(employeeService.findAllEmployees(anyInt())).thenReturn(employees);
        mockMvc.perform(get("/employees")
                .header("token",SecurityUtils.getTestToken()))
                .andDo(print())
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    public void should_return_employee_when_request_to_add_an_employee() throws Exception {
        Employee employee = new Employee();
        employee.setName("Test-name");
        when(employeeService.createEmployee(any())).thenReturn(employee);
        mockMvc.perform(post("/employees")
                .header("token",SecurityUtils.getTestToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(employee)))
                .andDo(print())
                .andExpect(jsonPath("$.data.name").value("Test-name"));
    }

    @Test
    public void should_return_All_orders_when_manager_request_all_orders() throws Exception {
        Employee parkingBoy = new Employee();
        parkingBoy.setRole(1);
        Orders firstOrder = new Orders();
        firstOrder.setParkingBoy(parkingBoy);
        firstOrder.setStatus(1);
        Orders secondOrder = new Orders();
        secondOrder.setParkingBoy(parkingBoy);
        secondOrder.setStatus(2);
        Orders thirdOrder = new Orders();
        thirdOrder.setFetchingBoy(parkingBoy);
        thirdOrder.setStatus(3);
        ArrayList<Orders> orders = new ArrayList<>();
        orders.add(firstOrder);
        orders.add(secondOrder);
        orders.add(thirdOrder);

        when(employeeService.getEmployeeOrdersByFinish(anyString(),anyBoolean())).thenReturn(orders);

        mockMvc.perform(get("/employees/{id}/orders","1").param("finish","false")
                .header("token",SecurityUtils.getTestToken()))
                .andDo(print())
                .andExpect(jsonPath("$.data.length()").value(3));
    }
    @Test
    public void should_return_employee_when_request_to_update_employee() throws Exception {
        Employee employee = new Employee();
        employee.setName("Test-name");
        when(employeeService.updateEmployee(anyString(),any())).thenReturn(employee);
        mockMvc.perform(put("/employees/1")
                .header("token",SecurityUtils.getTestToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(employee)))
                .andDo(print())
                .andExpect(jsonPath("$.data.name").value("Test-name"));
    }

}