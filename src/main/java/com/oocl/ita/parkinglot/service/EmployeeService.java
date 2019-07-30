package com.oocl.ita.parkinglot.service;

import com.oocl.ita.parkinglot.dto.GetEmployeeParkingLotDTO;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.vo.EmployeesVO;
import com.oocl.ita.parkinglot.vo.PageVO;
import com.oocl.ita.parkinglot.vo.ParkingLotVO;

import java.util.List;

public interface EmployeeService {
    PageVO<ParkingLot> getEmployeeAllParkingLots(String Id);

    Employee getEmployeeById(String employeeId);

    List<Orders> getEmployeeOrdersByFinish(String id,boolean finish);

    ParkingLot updateParkingLotByEmployeeId(String id, ParkingLot parkingLot);

    ParkingLot addEmployeeNewParkingLot(String id, ParkingLot parkingLot);

    List<ParkingLotVO> getParkingLotVOsByEmployeeId(String id,int page, int pageSize);

    List<EmployeesVO> getParkingBoyByManagedId(String id);

}
