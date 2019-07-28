package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.repository.ParkingLotRepository;
import com.oocl.ita.parkinglot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public List<ParkingLot> getEmployeeAllParkingLots(String parkingBoyId) {
        Employee employee = employeeRepository.findById(parkingBoyId).orElse(null);
        return (employee == null) ? null : employee.getParkingLots();
    }

    @Override
    public Employee getEmployeeById(String employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    @Override
    public List<Orders> getEmployeeUnfinishOrders(String id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if(employee == null)
            return null;
        else {
            List<Orders> orders = ordersRepository.findEmployeeOrders(id);
            List<Orders> unfinishOrders = orders.stream()
                    .filter(element -> element.getStatus() == 1 || element.getStatus() == 2 || element.getStatus() == 4 || element.getStatus() == 5)
                    .collect(Collectors.toList());
            return unfinishOrders;
        }
    }
}
