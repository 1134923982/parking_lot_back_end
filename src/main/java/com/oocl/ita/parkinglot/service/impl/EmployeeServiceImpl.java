package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.repository.ParkingLotRepository;
import com.oocl.ita.parkinglot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Override
    public List<ParkingLot> getEmployeeAllParkingLots(String parkingBoyId) {
        Employee employee = employeeRepository.findById(parkingBoyId).orElse(null);
        return (employee == null) ? null : employee.getParkingLots();
    }
}
