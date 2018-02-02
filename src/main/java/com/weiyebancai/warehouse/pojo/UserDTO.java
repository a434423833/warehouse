package com.weiyebancai.warehouse.pojo;

import java.io.Serializable;

/**
 * 
 * 
 * @author caohao 2018/2/2
 */
public class UserDTO implements Serializable {

    private String account;
    private String password;

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
