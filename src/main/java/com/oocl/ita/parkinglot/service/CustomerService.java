package com.oocl.ita.parkinglot.service;

import com.oocl.ita.parkinglot.model.Orders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<Orders> getCustomerHistoryOrdersByCustomerId(String customerId);
    List<Orders> getCustomerProcessingOrdersByCustomerId(String customerId);
}
