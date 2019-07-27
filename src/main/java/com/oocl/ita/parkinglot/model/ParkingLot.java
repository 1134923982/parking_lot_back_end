package com.oocl.ita.parkinglot.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ParkingLot {

    @Id
    @GeneratedValue
    private String id;

    private String name;

    private String postion;

    private int capacity;

    private int nowAvailable;

    public ParkingLot() {
    }

    public ParkingLot(String name, String postion, int capacity, int nowAvailable) {
        this.name = name;
        this.postion = postion;
        this.capacity = capacity;
        this.nowAvailable = nowAvailable;
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

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
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
