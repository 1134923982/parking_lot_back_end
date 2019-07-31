package com.oocl.ita.parkinglot.controller;

import com.oocl.ita.parkinglot.annotation.Auth;
import com.oocl.ita.parkinglot.enums.RoleEnum;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.service.EmployeeService;
import com.oocl.ita.parkinglot.utils.SecurityUtils;
import com.oocl.ita.parkinglot.vo.ParkingLotVO;
import com.oocl.ita.parkinglot.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.oocl.ita.parkinglot.enums.CodeMsgEnum.PARAMETER_ERROR;

@Api(value = "Employee Api",description = "Employee相关API")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @ApiOperation(value = "getEmployeeById" ,  notes="通过employeeId获取employee")
    @GetMapping("/employees/{employeeId}")
    public ResultVO<Employee> getEmployeeById(@PathVariable String employeeId) {
        Employee resultEmployee = new Employee();
        if (SecurityUtils.getEmployee().getRole() == RoleEnum.parkingBoy.ordinal()) {
            BeanUtils.copyProperties(employeeService.getEmployeeById(SecurityUtils.getEmployee().getId()), resultEmployee, "password");
            return ResultVO.success(resultEmployee);
        } else if (SecurityUtils.getEmployee().getRole() >= RoleEnum.manager.ordinal()) {
            BeanUtils.copyProperties(employeeService.getEmployeeById(employeeId), resultEmployee, "password");
            return ResultVO.success(resultEmployee);
        } else {
            return ResultVO.error(PARAMETER_ERROR);
        }
    }

    @ApiOperation(value = "getEmployeeOrdersByFinishStatus" ,  notes="根据订单状态获取特定id的employee的订单")
    @GetMapping("/employees/{id}/orders")
    public ResultVO<List<Orders>> getEmployeeOrdersByFinishStatus(@PathVariable(value = "id") String id, @RequestParam(value = "finish", defaultValue = "true") boolean finish) {
        List<Orders> orders = employeeService.getEmployeeOrdersByFinish(id, finish);
        if (orders == null) {
            return ResultVO.error(PARAMETER_ERROR);
        } else {
            return ResultVO.success(orders);
        }

    }

    @ApiOperation(value = "getParkingLotsByEmoloyeeId" ,  notes="根据employeeId获取employee的ParkingLot并且返回ParkingLot的所有Parkingboys")
    @GetMapping("/employees/{id}/parking-lots/{status}")
    public ResultVO<List<ParkingLotVO>> getParkingLotsByEmoloyeeId(@PathVariable String id, @PathVariable int status, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "6") int pageSize) {
        if (status == 1 && SecurityUtils.getEmployee().getRole() >= RoleEnum.manager.ordinal()) {
            List<ParkingLotVO> parkingLotVOsByEmployeeId = employeeService.getParkingLotVOsByEmployeeId(SecurityUtils.getEmployee().getId(), page, pageSize);
            return ResultVO.success(parkingLotVOsByEmployeeId);
        } else {
            return ResultVO.error(PARAMETER_ERROR);
        }
    }

    @ApiOperation(value = "getParingLotsByEmployeeId" ,  notes="根据employeeId获取employee的ParkingLot")
    @GetMapping("/employees/{id}/parking-lots")
    public ResultVO getParingLotByEmployeeId(@PathVariable(value = "id") String id) {
        return ResultVO.success(employeeService.getEmployeeAllParkingLots(id));
    }

    @ApiOperation(value = "updateEmployeeParkingLot" ,  notes="更改Employee的parkingLot信息")
    @PatchMapping("/employees/{id}/parking-lots")
    public ResultVO updateEmployeeParkingLot(@PathVariable(value = "id") String id, @RequestBody ParkingLot parkingLot) {
        return ResultVO.success(employeeService.updateParkingLotByEmployeeId(id, parkingLot));
    }

    @ApiOperation(value = "updateEmployee" ,  notes="更改Employee的信息")
    @PatchMapping("/employees/{id}")
    public ResultVO updateEmployee(@PathVariable("id") String id, @RequestBody Employee employee) {
        return ResultVO.success(employeeService.updateEmployee(employee));
    }

    @ApiOperation(value = "addEmployeeNewParkingLot" ,  notes="为employee添加新的parkingLot")
    @PostMapping("/employees/{id}/parking-lots")
    public ResultVO addEmployeeNewParkingLot(@PathVariable(value = "id") String id, @RequestBody ParkingLot parkingLot) {
        return ResultVO.success(employeeService.addEmployeeNewParkingLot(id, parkingLot));
    }

    @ApiOperation(value = "getParkingBoyByManagerId" ,  notes="根据ManagerId获取它所管理的parkingBoy")
    @GetMapping("/employees/{id}/employees")
    public ResultVO getParkingBoyByManagerId(@PathVariable("id") String id) {
        return ResultVO.success(employeeService.getParkingBoyByManagedId(id));
    }

    @ApiOperation(value = "getEmployeeList" ,  notes="获取所有Employee列表")
    @GetMapping("/employees")
    public ResultVO<List<Employee>> getEmployeeList(@RequestParam(defaultValue = "-1") int role){
        return ResultVO.success(employeeService.findAllEmployees(role));
    }

    @ApiOperation(value = "createEmployee" ,  notes="新增Employee")
    @PostMapping("/employees")
    public ResultVO<Employee> createEmployee(@RequestBody Employee employee){
        return ResultVO.success(employeeService.createEmployee(employee));
    }

    @ApiOperation(value = "updateEmployeeById" ,  notes="根据EmployeeId更改Employee")
    @PutMapping("/employees/{employeeId}")
    public ResultVO<Employee> updateEmployeeById(@PathVariable String employeeId,@RequestBody Employee employee){
        return ResultVO.success(employeeService.updateEmployeeById(employeeId,employee));
    }

}
