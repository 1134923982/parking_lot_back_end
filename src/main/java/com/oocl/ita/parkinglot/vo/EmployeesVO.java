package com.oocl.ita.parkinglot.vo;

public class EmployeesVO {

    private String id;

    private String name;

    private String idCardNumber;

    private String gender;

    private String telephone;

    private int status;

    public EmployeesVO(String id, String name, String idCardNumber, String gender, String telephone, int status) {
        this.id = id;
        this.name = name;
        this.idCardNumber = idCardNumber;
        this.gender = gender;
        this.telephone = telephone;
        this.status = status;
    }

    public EmployeesVO() {
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
}
