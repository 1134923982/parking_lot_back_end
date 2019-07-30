package com.oocl.ita.parkinglot.utils.converters;

import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.vo.EmployeesVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeToEmployeeVOConverterTest {

    @Test
    public void should_return_employeeVO_type_when_given_employee() {
        Employee employee = new Employee("name", "idCard", "gender", "phone", 0, "managedId");

        assertEquals(EmployeesVO.class, EmployeeToEmployeeVOConverter.convert(employee).getClass());
    }

    @Test
    public void should_return_employeeVO_with_parkingLots_when_given_employee() {
        Employee employee = new Employee("name", "idCard", "gender", "phone", 0, "managedId");
        employee.setParkingLots(Arrays.asList(new ParkingLot("lot", "posi", 10, 1)));

        EmployeesVO employeesVO = EmployeeToEmployeeVOConverter.convert(employee);

        assertEquals("lot", employeesVO.getParkingLots().get(0).getName());
    }

    @Test
    public void should_return_employeeVO_when_given_employee() {
        Employee employee = new Employee("name", "idCard", "gender", "phone", 0, "managedId");

        EmployeesVO employeesVO = EmployeeToEmployeeVOConverter.convert(employee);

        assertEquals("name", employeesVO.getName());
    }

    @Test
    public void should_return_employeeVOs_when_given_employees() {
        Employee firstEmployee = new Employee("name", "idCard", "gender", "phone", 0, "managedId");
        Employee secondEmployee = new Employee("2name", "2idCard", "2gender", "2phone", 0, "2managedId");

        List<Employee> employees = new ArrayList<>();
        employees.add(firstEmployee);
        employees.add(secondEmployee);

        List<EmployeesVO> employeesVOS = EmployeeToEmployeeVOConverter.convert(employees);

        assertEquals("2idCard", employeesVOS.get(1).getIdCardNumber());
    }
}