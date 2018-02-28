package com.weiyebancai.warehouse.pojo;

import com.weiyebancai.warehouse.pagemodel.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询订单DTO
 *
 * @author caohao 2018/2/13
 */
public class SelectOrderDTO implements Serializable {
    /**
     * 订单号
     */
    private Long noteno;
    /**
     * 订单备注
     */
    private String remork;
    /**
     * 下单开始时间
     */
    private Date beginTime;
    /**
     * 下单结束时间
     */
    private Date endTime;
    /**
     * 订单状态
     */
    private String orderState;
    /**
     * 订单金额开始
     */
    private Double beginMoney;
    /**
     * 订单金额结束
     */
    private Double endMoney;
    /**
     * 分页对象
     */
    private Page page;

    /**
     * 获取 下单开始时间
     */
    public Date getBeginTime() {
        return this.beginTime;
    }

    /**
     * 设置 下单开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取 下单结束时间
     */
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * 设置 下单结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取 订单状态
     */
    public String getOrderState() {
        return this.orderState;
    }

    /**
     * 设置 订单状态
     */
    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    /**
     * 获取 订单金额开始
     */
    public Double getBeginMoney() {
        return this.beginMoney;
    }

    /**
     * 设置 订单金额开始
     */
    public void setBeginMoney(Double beginMoney) {
        this.beginMoney = beginMoney;
    }

    /**
     * 获取 订单金额结束
     */
    public Double getEndMoney() {
        return this.endMoney;
    }

    /**
     * 设置 订单金额结束
     */
    public void setEndMoney(Double endMoney) {
        this.endMoney = endMoney;
    }

    /**
     * 获取 分页对象
     */
    public Page getPage() {
        return this.page;
    }

    /**
     * 设置 分页对象
     */
    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * 获取 订单备注
     */
    public String getRemork() {
        return this.remork;
    }

    /**
     * 设置 订单备注
     */
    public void setRemork(String remork) {
        this.remork = remork;
    }

    /**
     * 获取 订单号
     */
    public Long getNoteno() {
        return this.noteno;
    }

    /**
     * 设置 订单号
     */
    public void setNoteno(Long noteno) {
        this.noteno = noteno;
    }
}
