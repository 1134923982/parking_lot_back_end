package com.oocl.ita.parkinglot.utils;

import com.oocl.ita.parkinglot.model.Customer;

public class SecurityCustomerUtils {
    private static String token;

    public static String getToken() {
        return SecurityCustomerUtils.token;
    }

    public static void setToken(String token) {
        SecurityCustomerUtils.token = token;
    }

    public static Customer getCustomer(){
        return JwtToken.decode(getToken(),Customer.class);
    }

    public static String getTestToken(){
        Customer customer = new Customer();
        return JwtToken.encode(customer);
    }
}
