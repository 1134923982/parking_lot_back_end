package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.enums.CodeMsgEnum;
import com.oocl.ita.parkinglot.enums.OrdersStatusEnum;
import com.oocl.ita.parkinglot.exception.ParkingLotException;
import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.repository.CustomerRepository;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.service.CustomerService;
import com.oocl.ita.parkinglot.vo.OrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return ordersRepository.findByCustomerIdAndStatusIsFinish(customerId);
    }

    @Override
    public List<Orders> getCustomerProcessingOrdersByCustomerId(String customerId) {
        return ordersRepository.findByCustomerIdAndStatusIsUnFinish(customerId);
    }

    @Override
    public Orders createCustomerOrders(String customerId,Orders orders){
        if(ordersRepository.findByCustomerIdAndStatusIsUnFinish(customerId).size()==0){
            orders.setStatus(0);
            orders.setCustomer(customerRepository.findById(customerId).get());
            return ordersRepository.save(orders);
        }else{
            throw new ParkingLotException(CREATE_ERROR);
        }
    }

    @Override
    public Orders updateOrdersStatus(String customerId, String orderId, int status) {
        boolean isOrderOwner = doesTheCustomerOwnTheOrder(customerId, orderId);
        Orders orders = ordersRepository.getOne(orderId);
        boolean isValidStatus = isValidWaitingForUpdateStatus(orders, status);

        if (isOrderOwner && isValidStatus) {
            orders.setStatus(OrdersStatusEnum.FETCH_ORDER_CUSTOMER_CONFIRMED.ordinal());
            ordersRepository.save(orders);
            return orders;
        }

        throw new ParkingLotException(CodeMsgEnum.ORDER_STATUS_ERROR);
    }

    @Override
    public Boolean doesTheCustomerOwnTheOrder(String customerId, String orderId) {
        customerRepository.findById(customerId).orElseThrow(() -> new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR));
        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR));
        return orders.getCustomer().getId().equals(customerId);
    }

    @Override
    public Boolean isValidWaitingForUpdateStatus(Orders orders, int status) {
        if (status == OrdersStatusEnum.FETCH_ORDER_CUSTOMER_CONFIRMED.ordinal()) {

            boolean boyFetchedCarFromParkingLot = orders.getStatus() == OrdersStatusEnum.FETCH_ORDER_PICKED_UP_THE_CAR.ordinal();
            boolean boyReturnedCar = orders.getStatus() == OrdersStatusEnum.FETCH_ORDER_COMPLETED.ordinal();
            return boyFetchedCarFromParkingLot || boyReturnedCar;
        }
        /* 目前，除状态直接变更到确认完成外，状态只能递增 **/
        return status - orders.getStatus() == 1;
    }

    @Override
    public Map<String,Double> getComparisonPrice(long parkingTime, long fetchingTime, String position){
        if(parkingTime == 0 || fetchingTime == 0 || fetchingTime <= parkingTime){
            throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
        } else {
            return calculateComparisonPrice(parkingTime,fetchingTime);
        }
    }

    public Map<String,Double> calculateComparisonPrice (long parkingTime, long fetchingTime){
        Map<String,Double> comparisonPrice = new HashMap<>();
        double startingFare = 5;
        double secondsPerMimute = 60;
        double mimutesPerHour = 60;
        double parkingHours = (fetchingTime - parkingTime)/(secondsPerMimute * mimutesPerHour);
        double pricePerHour = 5;
        double pricePerDay = 50;
        double parkingDay = Math.floor(parkingHours/24);
        double realPrice = Math.round(parkingDay * pricePerDay + pricePerHour * (parkingHours/24 - parkingDay));
        if(realPrice == 0){
            realPrice += startingFare;
        }
        double discount=(70+(int)(Math.random()*(80+1-70)))/100.0;
        comparisonPrice.put("discounPrice", discount *realPrice);
        comparisonPrice.put("realPrice", realPrice);
        return comparisonPrice;
    }
}
