package com.oocl.ita.parkinglot.controller;

import com.oocl.ita.parkinglot.enums.RoleEnum;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.service.EmployeeService;
import com.oocl.ita.parkinglot.utils.SecurityUtils;
import com.oocl.ita.parkinglot.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.oocl.ita.parkinglot.enums.CodeMsgEnum.PARAMETER_ERROR;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees/{id}/parking-lots")
    public ResultVO<List<ParkingLot>> getEmployeeAllParkingLots(@PathVariable(value = "id") String id) {
        List<ParkingLot> employeeAllParkingLots = employeeService.getEmployeeAllParkingLots(id);
        return (employeeAllParkingLots == null) ? ResultVO.error(PARAMETER_ERROR) : ResultVO.success(employeeAllParkingLots);
    }

    @GetMapping("/employees/{employeeId}")
    public ResultVO<Employee> getEmployeeById(@PathVariable String employeeId) {
        Employee resultEmployee = new Employee();
        if (SecurityUtils.getEmployee().getRole() == RoleEnum.parkingBoy.ordinal()) {
            BeanUtils.copyProperties(SecurityUtils.getEmployee(), resultEmployee, "password");
            return ResultVO.success(resultEmployee);
        } else if (SecurityUtils.getEmployee().getRole() >= RoleEnum.manager.ordinal()) {
            BeanUtils.copyProperties(employeeService.getEmployeeById(employeeId), resultEmployee, "password");
            return ResultVO.success(resultEmployee);
        } else {
            return ResultVO.error(PARAMETER_ERROR);
        }
    }

    @GetMapping("/employees/{id}/orders")
    public ResultVO<List<Orders>> getEmployeeOrdersByFinishStatus(@PathVariable(value = "id") String id, @RequestParam(value = "finish") boolean finish) {
        List<Orders> orders = employeeService.getEmployeeOrdersByFinish(id, finish);
        if (orders == null) {
            return ResultVO.error(PARAMETER_ERROR);
        } else {
            return ResultVO.success(orders);
        }

    }
}
