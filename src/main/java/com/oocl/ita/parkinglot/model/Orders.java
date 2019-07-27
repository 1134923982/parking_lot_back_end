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

    private String status;

    private String carNumber;

    private String parkingLotId;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:MM:ss")
    private Date parkingTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:MM:ss")
    private Date fetchingTime;


    private String parkingBoyId;


    private String fetchingBoyId;

    public Orders() {
    }

    public Orders(String orderNumber, String status, String carNumber, String parkingLotId, Date parkingTime, Date fetchingTime, String parkingBoyId, String fetchingBoyId) {
        this.orderNumber = orderNumber;
        this.status = status;
        this.carNumber = carNumber;
        this.parkingLotId = parkingLotId;
        this.parkingTime = parkingTime;
        this.fetchingTime = fetchingTime;
        this.parkingBoyId = parkingBoyId;
        this.fetchingBoyId = fetchingBoyId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public Date getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(Date parkingTime) {
        this.parkingTime = parkingTime;
    }

    public Date getFetchingTime() {
        return fetchingTime;
    }

    public void setFetchingTime(Date fetchingTime) {
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
}
