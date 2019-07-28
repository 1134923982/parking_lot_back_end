package com.oocl.ita.parkinglot.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class ParkingLot {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    private String position;

    private int capacity;

    private int nowAvailable;

    public ParkingLot() {
    }

    public ParkingLot(String name, String position, int capacity, int nowAvailable) {
        this.name = name;
        this.position = position;
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
