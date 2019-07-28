package com.oocl.ita.parkinglot.vo;

public class OrdersVO {

    public String carNumber;

    public String customerName;

    private String customerTelephone;

    private String fetchPosition;

    private String parkingLotPosition;

    private Long fetchingTime;

    private int status;

    public OrdersVO(String carNumber, String customerName, String customerTelephone, String fetchPosition, String parkingLotPosition, Long fetchingTime) {
        this.carNumber = carNumber;
        this.customerName = customerName;
        this.customerTelephone = customerTelephone;
        this.fetchPosition = fetchPosition;
        this.parkingLotPosition = parkingLotPosition;
        this.fetchingTime = fetchingTime;
    }

    public OrdersVO() {
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTelephone() {
        return customerTelephone;
    }

    public void setCustomerTelephone(String customerTelephone) {
        this.customerTelephone = customerTelephone;
    }

    public String getFetchPosition() {
        return fetchPosition;
    }

    public void setFetchPosition(String fetchPosition) {
        this.fetchPosition = fetchPosition;
    }

    public String getParkingLotPosition() {
        return parkingLotPosition;
    }

    public void setParkingLotPosition(String parkingLotPosition) {
        this.parkingLotPosition = parkingLotPosition;
    }

    public Long getFetchingTime() {
        return fetchingTime;
    }

    public void setFetchingTime(Long fetchingTime) {
        this.fetchingTime = fetchingTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
