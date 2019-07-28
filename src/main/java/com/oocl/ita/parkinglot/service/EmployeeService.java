package com.oocl.ita.parkinglot.service;

import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.ParkingLot;

import java.util.List;

public interface EmployeeService {
    List<ParkingLot> getEmployeeAllParkingLots(String parkingBoyId);

    Employee getEmployeeById(String employeeId);
}
