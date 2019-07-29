package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.enums.CodeMsgEnum;
import com.oocl.ita.parkinglot.exception.ParkingLotException;
import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.repository.CustomerRepository;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.oocl.ita.parkinglot.enums.CodeMsgEnum.CREATE_ERROR;

/**
 * @author Gordon
 */
@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Orders> getCustomerHistoryOrdersByCustomerId(String customerId) {
        return ordersRepository.findByCustomer_IdAndStatusIsFinish(customerId);
    }

    @Override
    public List<Orders> getCustomerProcessingOrdersByCustomerId(String customerId) {
        return ordersRepository.findByCustomer_IdAndStatusIsUnFinish(customerId);
    }

    @Override
    public Orders createCustomerOrders(String customerId,Orders orders){
        if(ordersRepository.findByCustomer_IdAndStatusIsUnFinish(customerId).size()==0){
            orders.setStatus(0);
            orders.setCustomer(customerRepository.findById(customerId).get());
            return ordersRepository.save(orders);
        }else{
            throw new ParkingLotException(CREATE_ERROR);
        }
    }

}
