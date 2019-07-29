package com.oocl.ita.parkinglot.service;

import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Orders;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Gordon
 */
@Service
public interface CustomerService {
    /**
     * 获取用户的历史订单
     * @param customerId CustomerId
     * @return 订单列表
     */
    List<Orders> getCustomerHistoryOrdersByCustomerId(String customerId);

    /**
     * 获取正在进行中的订单
     * @param customerId CustomerId
     * @return 正在进行中的订单列表
     */
    List<Orders> getCustomerProcessingOrdersByCustomerId(String customerId);

    /**
     * 创建订单
     * @param customerId CustomerId
     * @param orders 创建的订单
     * @return 创建的订单
     */
    Orders createCustomerOrders(String customerId, Orders orders);
}
