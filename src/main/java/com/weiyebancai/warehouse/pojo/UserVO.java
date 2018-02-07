package com.weiyebancai.warehouse.pojo;

import java.io.Serializable;

/**
 * Created by caohao on 2018/2/6.
 */
public class UserVO implements Serializable {
    private String username;
    private Integer auth;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }
}
