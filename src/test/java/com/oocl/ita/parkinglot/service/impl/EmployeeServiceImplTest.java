package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceImplTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;



    @Test
    public void should_get_employee_all_parkinglots_when_employee_is_exist() {
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        ParkingLot thirdParkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);
        parkingLots.add(thirdParkingLot);
        Employee employee = new Employee();
        employee.setParkingLots(parkingLots);

        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));
        List<ParkingLot> findParkingLots = employeeService.getEmployeeAllParkingLots(employee.getId());

        assertEquals(employee.getParkingLots(),findParkingLots);

    }

    @Test
    public void should_return_null_when_employee_is_not_exist() {
        Employee employee = new Employee();
        when(employeeRepository.findById(anyString())).thenReturn(java.util.Optional.of(employee));
        List<ParkingLot> findParkingLots = employeeService.getEmployeeAllParkingLots("0");
        assertNull(findParkingLots);

    }

}