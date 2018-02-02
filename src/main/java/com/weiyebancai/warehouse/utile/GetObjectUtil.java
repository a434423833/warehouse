package com.yijiupi.kjjsp.utile;

import org.apache.http.protocol.HTTP;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CaoHao on 2017/12/7.
 */
public class GetObjectUtil {

    public static String getTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

}
