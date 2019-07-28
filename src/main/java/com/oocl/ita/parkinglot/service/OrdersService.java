package com.oocl.ita.parkinglot.service;

import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.vo.OrdersVO;

import java.util.List;

public interface OrdersService {

    OrdersVO updateOrders (String orderId, String parkingBoyId, String parkingLotId);
}
