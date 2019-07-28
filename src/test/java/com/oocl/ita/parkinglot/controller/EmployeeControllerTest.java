package com.oocl.ita.parkinglot.controller;

import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.service.EmployeeService;

import com.oocl.ita.parkinglot.utils.SecurityUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;


import static com.oocl.ita.parkinglot.enums.CodeMsgEnum.PARAMETER_ERROR;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        ParkingLot secondParkingLot = new ParkingLot();
        ParkingLot thirdParkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);
        parkingLots.add(thirdParkingLot);

        when(employeeService.getEmployeeAllParkingLots(anyString())).thenReturn(parkingLots);

        mockMvc.perform(get("/employees/{id}/parking-lots","1")
                .header("token", SecurityUtils.getTestToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void should_return_employee_all_parkinglots_when_employee_is_not_exist () throws Exception{

        when(employeeService.getEmployeeAllParkingLots(anyString())).thenReturn(null);

        mockMvc.perform(get("/employees/{id}/parking-lots","0")
                .header("token",SecurityUtils.getTestToken()))
                .andDo(print())
                .andExpect(jsonPath("$.retCode").value(PARAMETER_ERROR.getCode()));

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
        Orders secondOrder = new Orders();
        secondOrder.setParkingBoy(parkingBoy);
        Orders thirdOrder = new Orders();
        thirdOrder.setFetchingBoy(parkingBoy);
        ArrayList<Orders> orders = new ArrayList<>();
        orders.add(firstOrder);
        orders.add(secondOrder);
        orders.add(thirdOrder);

        when(employeeService.getEmployeeUnfinishOrders(anyString())).thenReturn(orders);

        mockMvc.perform(get("/employees/{id}/orders","1").param("finish","finish")
                .header("token",SecurityUtils.getTestToken()))
                .andDo(print())
                .andExpect(jsonPath("$.data.length()").value(3));
    }

}