package com.oocl.ita.parkinglot.controller;

import com.oocl.ita.parkinglot.enums.RoleEnum;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.service.EmployeeService;
import com.oocl.ita.parkinglot.utils.SecurityUtils;
import com.oocl.ita.parkinglot.vo.ParkingLotVO;
import com.oocl.ita.parkinglot.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.oocl.ita.parkinglot.enums.CodeMsgEnum.PARAMETER_ERROR;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

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
    public ResultVO<List<Orders>> getEmployeeOrdersByFinishStatus(@PathVariable(value = "id") String id, @RequestParam(value = "finish", defaultValue = "true") boolean finish) {
        List<Orders> orders = employeeService.getEmployeeOrdersByFinish(id, finish);
        if (orders == null) {
            return ResultVO.error(PARAMETER_ERROR);
        } else {
            return ResultVO.success(orders);
        }

    }

    @GetMapping("/employees/{id}/parking-lots/{status}")
    public ResultVO<List<ParkingLotVO>> getParkingLotsByEmoloyeeId(@PathVariable String id, @PathVariable int status, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "6") int pageSize) {
        if (status == 1 && SecurityUtils.getEmployee().getRole() >= RoleEnum.manager.ordinal()) {
            List<ParkingLotVO> parkingLotVOsByEmployeeId = employeeService.getParkingLotVOsByEmployeeId(SecurityUtils.getEmployee().getId(), page, pageSize);
            return ResultVO.success(parkingLotVOsByEmployeeId);
        } else {
            return ResultVO.error(PARAMETER_ERROR);
        }
    }

    @GetMapping("/employees/{id}/parking-lots")
    public ResultVO addEmployeeNewParkingLot(@PathVariable(value = "id") String id) {
        return ResultVO.success(employeeService.getEmployeeAllParkingLots(id));
    }

    @PatchMapping("/employees/{id}/parking-lots")
    public ResultVO updateEmployeeParkingLot(@PathVariable(value = "id") String id, @RequestBody ParkingLot parkingLot) {
        return ResultVO.success(employeeService.updateParkingLotByEmployeeId(id, parkingLot));
    }

    @PostMapping("/employees/{id}/parking-lots")
    public ResultVO addEmployeeNewParkingLot(@PathVariable(value = "id") String id, @RequestBody ParkingLot parkingLot) {
        return ResultVO.success(employeeService.addEmployeeNewParkingLot(id, parkingLot));
    }

    @GetMapping("/employees/{id}/employees")
    public ResultVO getParkingBoyByManagerId(@PathVariable("id") String id) {
        return ResultVO.success(employeeService.getParkingBoyByManagedId(id));
    }

    @GetMapping("/employees")
    public ResultVO<List<Employee>> getEmployeeList(@RequestParam(defaultValue = "-1") int role){
        return ResultVO.success(employeeService.findAllEmployees(role));
    }

    @PostMapping("/employees")
    public ResultVO<Employee> createEmployee(@RequestBody Employee employee){
        return ResultVO.success(employeeService.createEmployee(employee));
    }

}
