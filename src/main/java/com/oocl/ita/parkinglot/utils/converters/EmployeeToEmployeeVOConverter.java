package com.oocl.ita.parkinglot.utils.converters;

import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.vo.EmployeesVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeToEmployeeVOConverter {

    public static EmployeesVO convert(Employee employee) {
        EmployeesVO employeesVO = new EmployeesVO();
        BeanUtils.copyProperties(employee, employeesVO);
        return employeesVO;
    }

    public static List<EmployeesVO> convert(List<Employee> employees) {
        return employees.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
