package com.oocl.ita.parkinglot.dto;

public class UpdateOrdersStatusDTO {

    private String orderId;

    private String parkingBoyId;

    private String parkingLotId;

    public UpdateOrdersStatusDTO(String orderId, String parkingBoyId, String parkingLotId) {
        this.orderId = orderId;
        this.parkingBoyId = parkingBoyId;
        this.parkingLotId = parkingLotId;
    }

    public UpdateOrdersStatusDTO() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getParkingBoyId() {
        return parkingBoyId;
    }

    public void setParkingBoyId(String parkingBoyId) {
        this.parkingBoyId = parkingBoyId;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }
}
