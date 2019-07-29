package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.enums.OrdersStatusEnum;
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
    public List<Orders> getEmployeeOrdersByFinish(String id, boolean finish) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null)
            return null;
        else {
            List<Orders> orders = ordersRepository.findEmployeeOrders(id);
            if (!finish) {
                List<Orders> unfinishOrders = orders.stream()
                        .filter(element -> element.getStatus() == OrdersStatusEnum.PARK_ORDER_RECEIVED.ordinal()
                                || element.getStatus() == OrdersStatusEnum.PARK_ORDER_PICKED_UP_THE_CAR.ordinal()
                                || element.getStatus() == OrdersStatusEnum.FETCH_ORDER_RECEIVED.ordinal()
                                || element.getStatus() == OrdersStatusEnum.FETCH_ORDER_PICKED_UP_THE_CAR.ordinal())
                        .collect(Collectors.toList());
                return unfinishOrders;
            } else {
                List<Orders> finishOrders = orders.stream().filter(element -> element.getStatus() == OrdersStatusEnum.PARK_ORDER_CAR_IS_PARKED_AND_FETCH_ORDER_NOT_RECEIVED.ordinal()
                        || element.getStatus() == OrdersStatusEnum.FETCH_ORDER_COMPLETED.ordinal())
                        .collect(Collectors.toList());
                return finishOrders;
            }

        }
    }
}
