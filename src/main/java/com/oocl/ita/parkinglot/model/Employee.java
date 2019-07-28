package com.oocl.ita.parkinglot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Employee {
    @Id
    private String id = UUID.randomUUID().toString();

    private String password;

    private int role;

    private int state;

    private String name;

    private String idCardNumber;

    private String gender;

    private String telephone;

    private int status;

    private String managedId;

    public Employee() {
    }

    public Employee(String name, String idCardNumber, String gender, String telephone, int status, String managedId) {
        this.name = name;
        this.idCardNumber = idCardNumber;
        this.gender = gender;
        this.telephone = telephone;
        this.status = status;
        this.managedId = managedId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getManagedId() {
        return managedId;
    }

    public void setManagedId(String managedId) {
        this.managedId = managedId;
    }
}
