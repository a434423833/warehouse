package com.weiyebancai.warehouse.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存记录变更PO
 *
 * @author caohao 2018/2/8
 */
@Document(collection = "record")
public class RecordPO implements Serializable {
    /**
     * 主键id
     */
    @Id
    private String id;
    /**
     * 产品对应id
     */
    private String productId;
    /**
     * 操作事件
     */
    private String incident;
    /**
     * 原库存数
     */
    private Integer beginCount;
    /**
     * 库存变更数量
     */
    private Integer recordCount;
    /**
     * 新库存数
     */
    private Integer endCount;
    /**
     * 操作人
     */
    private String userName;
    /**
     * 操作时间
     */
    private Date time;
    /**
     * 操作描述
     */
    private String remark;

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
     * 获取 产品对应id
     */
    public String getProductId() {
        return this.productId;
    }

    /**
     * 设置 产品对应id
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 获取 操作事件
     */
    public String getIncident() {
        return this.incident;
    }

    /**
     * 设置 操作事件
     */
    public void setIncident(String incident) {
        this.incident = incident;
    }

    /**
     * 获取 库存变更数量
     */
    public Integer getRecordCount() {
        return this.recordCount;
    }

    /**
     * 设置 库存变更数量
     */
    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    /**
     * 获取 操作人
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * 设置 操作人
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取 操作事件
     */
    public Date getTime() {
        return this.time;
    }

    /**
     * 设置 操作事件
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 获取 原库存数
     */
    public Integer getBeginCount() {
        return this.beginCount;
    }

    /**
     * 设置 原库存数
     */
    public void setBeginCount(Integer beginCount) {
        this.beginCount = beginCount;
    }

    /**
     * 获取 新库存数
     */
    public Integer getEndCount() {
        return this.endCount;
    }

    /**
     * 设置 新库存数
     */
    public void setEndCount(Integer endCount) {
        this.endCount = endCount;
    }

    /**
     * 获取 操作描述
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置 操作描述
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
