package com.oocl.ita.parkinglot.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Orders {

    @Id
    private String id = UUID.randomUUID().toString();

    private String orderNumber;

    private int status;

    @ManyToOne
    private Customer customer;

    private String carNumber;

    private String fetchPosition;

    @ManyToOne
    private ParkingLot parkingLot;


    private long parkingTime;


    private long fetchingTime;


    @OneToOne
    @JoinColumn(name = "parkingBoyId")
    private Employee parkingBoy;


    @OneToOne
    @JoinColumn(name = "fetchingBoyId")
    private Employee fetchingBoy;

    public Orders() {
    }

    public Orders(String orderNumber, int status, Customer customer, String carNumber, String fetchPosition) {
        this.orderNumber = orderNumber;
        this.status = status;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
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

    public Employee getParkingBoy() {
        return parkingBoy;
    }

    public void setParkingBoy(Employee parkingBoy) {
        this.parkingBoy = parkingBoy;
    }

    public Employee getFetchingBoy() {
        return fetchingBoy;
    }

    public void setFetchingBoy(Employee fetchingBoy) {
        this.fetchingBoy = fetchingBoy;
    }


    public Customer getCustomerId() {
        return customer;
    }

    public void setCustomerId(String customerId) {
        this.customer = customer;
    }

    public String getFetchPosition() {
        return fetchPosition;
    }

    public void setFetchPosition(String fetchPosition) {
        this.fetchPosition = fetchPosition;
    }
}
