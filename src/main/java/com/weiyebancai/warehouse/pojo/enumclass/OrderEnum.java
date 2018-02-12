package com.weiyebancai.warehouse.pojo.enumclass;

/**
 * 订单状态枚举
 *
 * @author caohao 2018/2/8
 */
public enum OrderEnum {
    待确认(0), 待出库(1);
    private Integer state;

    OrderEnum(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return state;
    }
}
