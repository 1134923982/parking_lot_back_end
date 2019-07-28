package com.oocl.ita.parkinglot.controller;


import com.oocl.ita.parkinglot.dto.UpdateOrdersStatusDTO;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersService ordersService;

    @GetMapping
    public ResponseEntity getAllOrders () {
        return ResponseEntity.ok(ordersRepository.findAllNotReceiptOrders());
    }

    @PatchMapping
    public ResponseEntity updateOrder (@RequestBody UpdateOrdersStatusDTO dto) {
        return ResponseEntity.ok(ordersService.updateOrders(dto.getOrderId(), dto.getParkingBoyId(), dto.getParkingLotId()));
    }
}
