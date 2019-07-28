package com.oocl.ita.parkinglot.utils;

import javax.servlet.http.HttpServletResponse;

public class CrossOriginUtil {

    public static void responseSet(HttpServletResponse response, String origin) {
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }
}