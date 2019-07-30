package com.oocl.ita.parkinglot.controller;



import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.vo.ResultVO;

import com.oocl.ita.parkinglot.dto.UpdateOrdersStatusDTO;
import com.oocl.ita.parkinglot.service.OrdersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersService ordersService;

    @GetMapping
    public ResultVO<List<Orders>> getAllOrders () {
        List<Orders> allNotReceiptOrders = ordersRepository.findAllNotReceiptOrders();
        return ResultVO.success(allNotReceiptOrders);
    }

    @PatchMapping
    public ResultVO updateOrder (@RequestBody UpdateOrdersStatusDTO dto) {
        return ResultVO.success(ordersService.updateOrders(dto.getOrderId(), dto.getParkingBoyId(), dto.getParkingLotId()));
    }


}
