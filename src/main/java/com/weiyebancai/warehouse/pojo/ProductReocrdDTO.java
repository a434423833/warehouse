package com.weiyebancai.warehouse.pojo;

import com.weiyebancai.warehouse.pagemodel.Page;

import java.io.Serializable;

/**
 * 查询库存变更DTO
 *
 * @author caohao 2018/2/8
 */
public class ProductReocrdDTO implements Serializable {
    /**
     * 商品id
     */
    private String productId;

    private Page page;

    /**
     * 获取 商品id
     */
    public String getProductId() {
        return this.productId;
    }

    /**
     * 设置 商品id
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Page getPage() {
        return this.page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
