package com.weiyebancai.warehouse.pojo;

import java.io.Serializable;

/**
 * 商品DTO
 *
 * @author caohao 2018/2/2
 */
public class ProductDTO implements Serializable {
    /**
     * 主键id
     */
    private String id;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品类别
     */
    private String productCategory;
    /**
     * 产品数量
     */
    private Integer productCount;
    /**
     * 产品所在地
     */
    private String productWarehouse;
    /**
     * 产品品牌
     */
    private String productBrand;
    /**
     * 产品定价
     */
    private Double productBid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getProductWarehouse() {
        return productWarehouse;
    }

    public void setProductWarehouse(String productWarehouse) {
        this.productWarehouse = productWarehouse;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public Double getProductBid() {
        return productBid;
    }

    public void setProductBid(Double productBid) {
        this.productBid = productBid;
    }
}
