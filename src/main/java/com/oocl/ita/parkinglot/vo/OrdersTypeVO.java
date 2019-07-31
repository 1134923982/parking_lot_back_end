package com.oocl.ita.parkinglot.vo;

import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

public class OrdersTypeVO extends Orders {
    private int type;

    public OrdersTypeVO() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
