package com.weiyebancai.warehouse.utile;

import com.weiyebancai.warehouse.pojo.UserVO;
import org.apache.http.protocol.HTTP;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    public static String getUserName(HttpSession session) {
        return ((UserVO) session.getAttribute("user")).getUsername();
    }

}
