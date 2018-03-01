package com.weiyebancai.warehouse.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单po
 *
 * @author caohao 2018/2/12
 */
@Document(collection = "order")
public class OrderPO implements Serializable {
    /**
     * 主键id
     */
    @Id
    private String id;
    /**
     * 订单号
     */
    private Long noteno;
    /**
     * 订单备注
     */
    private String remork;
    /**
     * 订单生成时间
     */
    private Date createTime;
    /**
     * 订单创建人
     */
    private String createUser;
    /**
     * 订单状态
     */
    private String state;
    /**
     * 订单修改时间
     */
    private Date updateTime;
    /**
     * 出库人
     */
    private String stockUser;
    /**
     * 产品
     */
    private List<ProductPO> productList;

    /**
     * 获取 主键id
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置 主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 出库单备注
     */
    public String getRemork() {
        return this.remork;
    }

    /**
     * 设置 出库单备注
     */
    public void setRemork(String remork) {
        this.remork = remork;
    }

    /**
     * 获取 出库单生成时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置 出库单生成时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 出库单状态
     */
    public String getState() {
        return this.state;
    }

    /**
     * 设置 出库单状态
     */
    public void setState(String state) {
        this.state = state;
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

    /**
     * 获取 订单创建人
     */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * 设置 订单创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取 产品
     */
    public List<ProductPO> getProductList() {
        return this.productList;
    }

    /**
     * 设置 产品
     */
    public void setProductList(List<ProductPO> productList) {
        this.productList = productList;
    }

    /**
     * 获取 订单修改时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置 订单修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 出库人
     */
    public String getStockUser() {
        return this.stockUser;
    }

    /**
     * 设置 出库人
     */
    public void setStockUser(String stockUser) {
        this.stockUser = stockUser;
    }
}
