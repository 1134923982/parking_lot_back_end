package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.enums.OrdersStatusEnum;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.repository.ParkingLotRepository;
import com.oocl.ita.parkinglot.service.OrdersService;
import com.oocl.ita.parkinglot.vo.OrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public OrdersVO updateOrders (String orderId, String parkingBoyId, String parkingLotId) {
        Orders orders = ordersRepository.findById(orderId).orElse(null);
        if (orders == null) {
            return null;
        }

        if (!StringUtils.isEmpty(parkingLotId)) {
            Employee parkingBoy = employeeRepository.findById(parkingBoyId).orElse(null);
            orders.setParkingBoy(parkingBoy);

            ParkingLot parkingLot= parkingLotRepository.findById(parkingLotId).orElse(null);
            parkingLot.setNowAvailable(parkingLot.getNowAvailable() - 1);
            parkingLotRepository.save(parkingLot);
            orders.setParkingLot(parkingLot);

        } else if (!StringUtils.isEmpty(parkingBoyId)) {
            Employee parkingBoy = employeeRepository.findById(parkingBoyId).orElse(null);
            orders.setFetchingBoy(parkingBoy);
        } else {
            int status = orders.getStatus();
            if (status == OrdersStatusEnum.FETCH_ORDER_RECEIVED.ordinal()) {
                ParkingLot parkingLot = orders.getParkingLot();
                parkingLot.setNowAvailable(parkingLot.getNowAvailable() + 1);
                parkingLotRepository.save(parkingLot);
            }
        }

        orders.setStatus(orders.getStatus() + 1);
        ordersRepository.save(orders);

        OrdersVO ordersVO = new OrdersVO();
        ordersVO.setCarNumber(orders.getCarNumber());
        ordersVO.setCustomerName(orders.getCustomer().getUserName());
        ordersVO.setCustomerTelephone(orders.getCustomer().getTelephone());
        ordersVO.setFetchingTime(orders.getFetchingTime());
        ordersVO.setFetchPosition(orders.getFetchPosition());
        ordersVO.setParkingLotPosition(orders.getParkingLot().getPosition());
        ordersVO.setStatus(orders.getStatus());

        return ordersVO;
    }
}
