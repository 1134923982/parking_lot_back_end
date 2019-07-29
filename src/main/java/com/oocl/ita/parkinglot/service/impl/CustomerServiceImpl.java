package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Gordon
 */
@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public List<Orders> getCustomerHistoryOrdersByCustomerId(String customerId) {
        return ordersRepository.findByCustomer_IdAndStatusIsFinish(customerId);
    }

    @Override
    public List<Orders> getCustomerProcessingOrdersByCustomerId(String customerId) {
        return ordersRepository.findByCustomer_IdAndStatusIsUnFinish(customerId);
    }
}
