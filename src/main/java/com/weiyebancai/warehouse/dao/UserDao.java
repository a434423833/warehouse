package com.weiyebancai.warehouse.dao;

import com.weiyebancai.warehouse.pojo.ProductPO;
import com.weiyebancai.warehouse.pojo.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * @author caohao 2018/2/2
 */
@Component
public class UserDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 根据用户名查询对象
     *
     * @param userPO
     * @return
     */
    public UserPO findUserByUserName(UserPO userPO) {
        Query query = new Query(Criteria.where("account").is(userPO.getAccount()).and("password").is(userPO.getPassword()));
        UserPO user = mongoTemplate.findOne(query, UserPO.class);
        return user;
    }

    /**
     * 添加商品
     *
     * @param productPO
     */
    public void insertProduct(ProductPO productPO) {
        mongoTemplate.save(productPO);
    }
}
