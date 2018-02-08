package com.weiyebancai.warehouse.pojo.enumclass;

/**
 * 用户身份枚举
 *
 * @author caohao 2018/2/8
 */
public enum UserEnum {
    管理员(1), 仓库管理员(2);
    private Integer auth;

    UserEnum(Integer auth) {
        this.auth = auth;
    }

    public Integer getType() {
        return auth;
    }
}
