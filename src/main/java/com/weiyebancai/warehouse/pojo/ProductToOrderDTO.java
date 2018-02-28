package com.weiyebancai.warehouse.pojo;

import java.io.Serializable;

/**
 * 订单添加商品
 *
 * @author caohao 2018/2/27
 */
public class ProductToOrderDTO implements Serializable {

    /**
     * 商品id主键
     */
    private String productId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 变更数量
     */
    private Integer productRecord;

    /**
     * 获取 商品id主键
     */
    public String getProductId() {
        return this.productId;
    }

    /**
     * 设置 商品id主键
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 获取 订单id
     */
    public Long getOrderId() {
        return this.orderId;
    }

    /**
     * 设置 订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取 变更数量
     */
    public Integer getProductRecord() {
        return this.productRecord;
    }

    /**
     * 设置 变更数量
     */
    public void setProductRecord(Integer productRecord) {
        this.productRecord = productRecord;
    }

    @Override
    public String toString() {
        return "ProductToOrderDTO{" +
                "productId='" + productId + '\'' +
                ", orderId=" + orderId +
                ", productRecord=" + productRecord +
                '}';
    }
}
