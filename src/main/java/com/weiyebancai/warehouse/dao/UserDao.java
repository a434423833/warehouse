package com.weiyebancai.warehouse.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.weiyebancai.warehouse.pagemodel.DataResult;
import com.weiyebancai.warehouse.pagemodel.Page;
import com.weiyebancai.warehouse.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

}
