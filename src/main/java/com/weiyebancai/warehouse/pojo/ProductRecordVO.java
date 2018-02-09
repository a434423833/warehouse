package com.weiyebancai.warehouse.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 查询商品库存变记录VO
 *
 * @author caohao 2018/2/9
 */
public class ProductRecordVO implements Serializable {

    private String productId;

    private String productName;

    private String productWarehouse;

    private List<RecordPO> list;

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductWarehouse() {
        return this.productWarehouse;
    }

    public void setProductWarehouse(String productWarehouse) {
        this.productWarehouse = productWarehouse;
    }

    public List<RecordPO> getList() {
        return this.list;
    }

    public void setList(List<RecordPO> list) {
        this.list = list;
    }
}
