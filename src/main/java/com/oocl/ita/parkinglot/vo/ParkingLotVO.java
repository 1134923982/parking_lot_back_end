package com.oocl.ita.parkinglot.vo;


import com.oocl.ita.parkinglot.model.Employee;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParkingLotVO {

    private String id = UUID.randomUUID().toString();

    private String name;

    private String position;

    private int capacity;

    private int nowAvailable;

    private List<Employee> parkingBoys = new ArrayList<>();

    public ParkingLotVO() {
    }

    public ParkingLotVO(String id, String name, String position, int capacity, int nowAvailable) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.capacity = capacity;
        this.nowAvailable = nowAvailable;
    }

    public List<Employee> getParkingBoys() {
        return parkingBoys;
    }

    public void setParkingBoys(List<Employee> parkingBoys) {
        this.parkingBoys = parkingBoys;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNowAvailable() {
        return nowAvailable;
    }

    public void setNowAvailable(int nowAvailable) {
        this.nowAvailable = nowAvailable;
    }
}
