package com.oocl.ita.parkinglot.controller;

import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.service.CustomerService;
import com.oocl.ita.parkinglot.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Gordon
 */
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers/{customerId}/orders")
    public ResultVO<List<Orders>> getOrdersByCustomerId(@PathVariable String customerId,
                                                        @RequestParam(value="isFinish",defaultValue = "true") boolean isFinish){
        if(!isFinish){
            return ResultVO.success(customerService.getCustomerProcessingOrdersByCustomerId(customerId));
        }else{
            return ResultVO.success(customerService.getCustomerHistoryOrdersByCustomerId(customerId));
        }
    }
}
