package com.weiyebancai.warehouse.utile;

import java.util.UUID;

/**
 * @author caohao 2018/2/6
 */
public class UUidUtil {
    private static int sequence = 100;

    private UUidUtil() {
    }

    public static String getUUid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public synchronized static Long getUUidInt() {
        UUidUtil.sequence++;
        //产生一个时间戳
        long now = System.currentTimeMillis();
        String uuid = now + "" + sequence;
        if (sequence >= 999) {
            sequence = 100;
        }
        return Long.parseLong(uuid);
    }
}
