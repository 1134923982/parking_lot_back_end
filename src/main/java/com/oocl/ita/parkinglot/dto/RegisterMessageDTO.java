package com.oocl.ita.parkinglot.dto;

/**
 * @description:
 * @author: Deng
 * @create: 2019-08-01
 */
public class RegisterMessageDTO {

    private String phone;

    private String password;

    private String job;

    public RegisterMessageDTO(String phone, String password, String job) {
        this.phone = phone;
        this.password = password;
        this.job = job;
    }

    public RegisterMessageDTO() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}

