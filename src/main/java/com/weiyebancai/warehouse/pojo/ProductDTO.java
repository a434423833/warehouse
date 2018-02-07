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
    private Integer productWarhouse;
    /**
     * 产品品牌
     */
    private String productBrand;

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

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

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Integer getProductWarhouse() {
        return productWarhouse;
    }

    public void setProductWarhouse(Integer productWarhouse) {
        this.productWarhouse = productWarhouse;
    }
}
