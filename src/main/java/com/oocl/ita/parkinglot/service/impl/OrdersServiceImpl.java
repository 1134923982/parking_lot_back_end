package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.enums.CodeMsgEnum;
import com.oocl.ita.parkinglot.enums.OrdersStatusEnum;
import com.oocl.ita.parkinglot.exception.ParkingLotException;
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
        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR));

        if (!StringUtils.isEmpty(parkingLotId)) {
            Employee parkingBoy = employeeRepository.findById(parkingBoyId).orElse(null);
            ParkingLot parkingLot= parkingLotRepository.findById(parkingLotId).orElse(null);

            if (parkingBoy == null || parkingLot == null || parkingLot.getNowAvailable() <= 0) {
                throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
            }

            orders.setParkingBoy(parkingBoy);
            parkingLot.setNowAvailable(parkingLot.getNowAvailable() - 1);
            parkingLotRepository.save(parkingLot);
            orders.setParkingLot(parkingLot);

        } else if (!StringUtils.isEmpty(parkingBoyId)) {
            Employee parkingBoy = employeeRepository.findById(parkingBoyId).orElseThrow(() -> new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR));
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
        ordersVO.setParkingLotName(orders.getParkingLot().getName());
        ordersVO.setStatus(orders.getStatus());
        ordersVO.setParkingTime(orders.getParkingTime());

        return ordersVO;
    }
}
