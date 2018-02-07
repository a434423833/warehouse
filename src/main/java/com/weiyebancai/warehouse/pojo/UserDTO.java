package com.weiyebancai.warehouse.pojo;

import java.io.Serializable;

/**
 * @author caohao 2018/2/2
 */
public class UserDTO implements Serializable {

    private String account;
    private String username;
    private String password;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
