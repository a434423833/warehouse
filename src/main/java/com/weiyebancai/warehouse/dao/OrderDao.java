package com.weiyebancai.warehouse.dao;

import com.weiyebancai.warehouse.pojo.OrderPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * 出库单Dao
 *
 * @author caohao 2018/2/12
 */
@Component
public class OrderDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 添加出库单
     */
    public void insertOrder(OrderPO orderPO) {
        mongoTemplate.save(orderPO);
    }

}
