package com.oocl.ita.parkinglot.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Orders {

    @Id
    @GeneratedValue
    private String id;

    private String orderNumber;

    private int status;

    private String customerId;

    private String carNumber;

    private String fetchPosition;

    private String parkingLotId;


    private long parkingTime;


    private long fetchingTime;


    private String parkingBoyId;


    private String fetchingBoyId;

    public Orders() {
    }

    public Orders(String orderNumber, int status, String customerId, String carNumber, String fetchPosition) {
        this.orderNumber = orderNumber;
        this.status = status;
        this.customerId = customerId;
        this.carNumber = carNumber;
        this.fetchPosition = fetchPosition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public long getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(long parkingTime) {
        this.parkingTime = parkingTime;
    }

    public long getFetchingTime() {
        return fetchingTime;
    }

    public void setFetchingTime(long fetchingTime) {
        this.fetchingTime = fetchingTime;
    }

    public String getParkingBoyId() {
        return parkingBoyId;
    }

    public void setParkingBoyId(String parkingBoyId) {
        this.parkingBoyId = parkingBoyId;
    }

    public String getFetchingBoyId() {
        return fetchingBoyId;
    }

    public void setFetchingBoyId(String fetchingBoyId) {
        this.fetchingBoyId = fetchingBoyId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFetchPosition() {
        return fetchPosition;
    }

    public void setFetchPosition(String fetchPosition) {
        this.fetchPosition = fetchPosition;
    }
}
