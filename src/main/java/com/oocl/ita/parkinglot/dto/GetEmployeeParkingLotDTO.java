package com.oocl.ita.parkinglot.dto;

public class GetEmployeeParkingLotDTO {

    private String name;
    private String position;
    private int page;
    private int pageSize;

    public GetEmployeeParkingLotDTO(String name, String position, int page, int pageSize) {
        this.name = name;
        this.position = position;
        this.page = page;
        this.pageSize = pageSize;
    }

    public GetEmployeeParkingLotDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

