package com.oocl.ita.parkinglot.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MD5UtilTest {

    @Test
    public void encrypt() {
        String str = "789456";
        String e = "71b3b26aaa319e0cdf6fdb8429c112b0";
        assertEquals(e, MD5Util.encrypt(str));
    }
}