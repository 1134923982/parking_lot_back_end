package com.oocl.ita.parkinglot.utils;


import com.oocl.ita.parkinglot.model.Employee;

/**
 * 通过token获取当前用户的工具类
 * @author Gordon
 * @version 创建时间 ： 2019/7/28.
 */
public class SecurityUtils {
    private static String token;

    public static String getToken() {
        return SecurityUtils.token;
    }

    public static void setToken(String token) {
        SecurityUtils.token = token;
    }

    public static Employee getEmployee(){
        return JwtToken.decode(getToken(),Employee.class);
    }

    public static String getTestToken(){
        Employee employee = new Employee();
        employee.setRole(2);
        return JwtToken.encode(employee);
    }

}
