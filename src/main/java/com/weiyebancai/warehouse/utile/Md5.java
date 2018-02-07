package com.weiyebancai.warehouse.utile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Md5.class);

    public static String getMD5(String str) {
        byte[] secretBytes = null;

        try {
            secretBytes = MessageDigest.getInstance("md5").digest(str.getBytes());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("MD5转换出错", e);
        }
        String md5String = new BigInteger(1, secretBytes).toString(16);

        for (int i = 0; i < 32 - md5String.length(); i++) {
            md5String = "0" + md5String;
        }

        return md5String;

    }

}
