package com.oocl.ita.parkinglot.controller;

import com.oocl.ita.parkinglot.dto.CustomerUpdateOrderStatusDTO;
import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.service.CustomerService;
import com.oocl.ita.parkinglot.utils.SecurityCustomerUtils;
import com.oocl.ita.parkinglot.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Gordon
 */
@Api(value = "Customer Api",description = "Customer相关API")
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "getOrdersByCustomerId" ,  notes="根据customerId获取用户的订单")
    @GetMapping("/customers/{customerId}/orders")
    public ResultVO<List<Orders>> getOrdersByCustomerId(@PathVariable String customerId,
                                                        @RequestParam(value="isFinish",defaultValue = "true") boolean isFinish){
        if(!isFinish){
            return ResultVO.success(customerService.getCustomerProcessingOrdersByCustomerId(customerId));
        }else{
            return ResultVO.success(customerService.getCustomerHistoryOrdersByCustomerId(customerId));
        }
    }

    @ApiOperation(value = "createOrdersByCustomerId" ,  notes="customerId的用户下单")
    @PostMapping("/customers/{customerId}/orders")
    public ResultVO<Orders> createOrdersByCustomerId(@PathVariable String customerId,
                                                     @RequestBody Orders orders){
        return ResultVO.success(customerService.createCustomerOrders(customerId,orders));
    }

    @ApiOperation(value = "getCustomerByCustomerId" ,  notes="根据CustomerId获取Customer信息")
    @GetMapping("/customers/{customerId}")
    public ResultVO<Customer> getCustomerByCustomerId(HttpServletRequest request,@PathVariable String customerId){
        return ResultVO.success(SecurityCustomerUtils.getCustomer());
    }

    @PatchMapping("/customers/{customerId}/orders/{ordersId}")
    public ResultVO<Orders> updateOrdersStatusByCustomerIdAndOrdersId(@PathVariable("customerId") String customerId, @PathVariable("ordersId") String ordersId, @RequestBody CustomerUpdateOrderStatusDTO dto) {
        return ResultVO.success(customerService.updateOrdersStatus(customerId, ordersId, dto.getStatus()));
    }
}
