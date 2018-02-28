package com.weiyebancai.warehouse.pojo;

import com.weiyebancai.warehouse.pagemodel.Page;

import java.io.Serializable;

/**
 * 查找库存DTP
 *
 * @author caohao 2018/2/8
 */
public class ProductSelectDTO implements Serializable {
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品类别
     */
    private String productCategory;
    /**
     * 产品所在地
     */
    private String productWarehouse;
    /**
     * 产品品牌
     */
    private String productBrand;
    /**
     * 分页对象
     */
    private Page page;

    /**
     * 获取 产品名称
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * 设置 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取 产品类别
     */
    public String getProductCategory() {
        return this.productCategory;
    }

    /**
     * 设置 产品类别
     */
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    /**
     * 获取 产品所在地
     */
    public String getProductWarehouse() {
        return this.productWarehouse;
    }

    /**
     * 设置 产品所在地
     */
    public void setProductWarehouse(String productWarehouse) {
        this.productWarehouse = productWarehouse;
    }

    /**
     * 获取 产品品牌
     */
    public String getProductBrand() {
        return this.productBrand;
    }

    /**
     * 设置 产品品牌
     */
    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
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

}
