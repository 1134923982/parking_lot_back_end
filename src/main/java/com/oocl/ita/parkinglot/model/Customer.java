package com.oocl.ita.parkinglot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Customer {
    @Id
    private String id = UUID.randomUUID().toString();

    private String userName;

    private String password;

    private int status;

    private String telephone;

    public Customer() {
    }

    public Customer(String userName, String password, int status, String telephone) {
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.telephone = telephone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
