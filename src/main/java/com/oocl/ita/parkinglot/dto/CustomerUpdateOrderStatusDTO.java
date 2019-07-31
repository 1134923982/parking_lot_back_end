package com.oocl.ita.parkinglot.dto;

/* 临时解决方案 for CustomerController updateOrdersStatusByCustomerIdAndOrdersId 接收参数异常 **/
public class CustomerUpdateOrderStatusDTO {

    private String ordersId;

    private int status;

    public CustomerUpdateOrderStatusDTO(String ordersId, int status) {
        this.ordersId = ordersId;
        this.status = status;
    }

    public CustomerUpdateOrderStatusDTO() {
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
