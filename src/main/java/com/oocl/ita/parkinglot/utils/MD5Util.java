package com.oocl.ita.parkinglot.utils;

import com.oocl.ita.parkinglot.enums.CodeMsgEnum;
import com.oocl.ita.parkinglot.exception.ParkingLotException;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Util {

    public static String encrypt(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(message.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new ParkingLotException(CodeMsgEnum.ENCRYPT_ERROR);
        }
    }
}
