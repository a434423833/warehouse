package com.weiyebancai.warehouse.dao;

import com.weiyebancai.warehouse.pagemodel.DataResult;
import com.weiyebancai.warehouse.pagemodel.Page;
import com.weiyebancai.warehouse.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
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

    /**
     * 添加商品
     *
     * @param productPO
     */
    public void insertProduct(ProductPO productPO) {
        mongoTemplate.save(productPO);
    }

    /**
     * 添加商品库存变更记录
     *
     * @param po
     */
    public void insertRecord(RecordPO po) {
        mongoTemplate.save(po);
    }

    /**
     * 查找商品
     *
     * @param productSelectDTO
     * @return
     */
    public DataResult<List<ProductPO>> selectProduct(ProductSelectDTO productSelectDTO) {
        DataResult<List<ProductPO>> dataResult = new DataResult();
        Page page = productSelectDTO.getPage();
        Query query = new Query();
        if (productSelectDTO.getProductBrand() != null) {
            query.addCriteria(Criteria.where("productBrand").regex(".*?" + productSelectDTO.getProductBrand() + ".*"));
        }
        if (productSelectDTO.getProductCategory() != null) {
            query.addCriteria(Criteria.where("productCategory").regex(".*?" + productSelectDTO.getProductCategory() + ".*"));
        }
        if (productSelectDTO.getProductName() != null) {
            query.addCriteria(Criteria.where("productName").regex(".*?" + productSelectDTO.getProductName() + ".*"));
        }
        if (productSelectDTO.getProductWarehouse() != null) {
            query.addCriteria(Criteria.where("productWarehouse").regex(".*?" + productSelectDTO.getProductWarehouse() + ".*"));
        }
        if (page.getOrderBy() != null) {
            query.with(new Sort(new Sort.Order(page.getOrderBy().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productCategory")));
        }
        //分页
        query.skip((page.getPageNum() - 1) * page.getPageSize());
        query.limit(page.getPageSize());
        List<ProductPO> list = this.mongoTemplate.find(query, ProductPO.class);
        long count = this.mongoTemplate.count(query, ProductPO.class);
        page.setTotal(count);
        page.setPages(count % page.getPageSize() == 0 ? (int) count / page.getPageSize() : (int) count / page.getPageSize() + 1);
        dataResult.setPage(page);
        dataResult.setData(list);
        return dataResult;
    }

    /**
     * 查找商品库存变更记录
     *
     * @param productReocrdDTO
     * @return
     */
    public DataResult<List<RecordPO>> selectRecord(ProductReocrdDTO productReocrdDTO) {
        DataResult<List<RecordPO>> dataResult = new DataResult();
        Page page = productReocrdDTO.getPage();
        Query query = new Query();
        query.addCriteria(Criteria.where("productId").is(productReocrdDTO.getProductId()));
        if (page.getOrderBy() != null) {
            query.with(new Sort(new Sort.Order(page.getOrderBy().equals("asc") ? Sort.Direction.DESC : Sort.Direction.ASC, "time")));
        }
        //分页
        query.skip((page.getPageNum() - 1) * page.getPageSize());
        query.limit(page.getPageSize());
        List<RecordPO> list = this.mongoTemplate.find(query, RecordPO.class);
        long count = this.mongoTemplate.count(query, RecordPO.class);
        page.setTotal(count);
        page.setPages(count % page.getPageSize() == 0 ? (int) count / page.getPageSize() : (int) count / page.getPageSize() + 1);
        dataResult.setPage(page);
        dataResult.setData(list);
        return dataResult;
    }

    /**
     * 通过id获得库存商品记录
     *
     * @param productId
     * @return
     */
    public ProductPO findOneProduct(String productId) {
        return mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(productId)), ProductPO.class);
    }

    /**
     * 修改库存商品
     *
     * @param productPO
     */
    public void updateProduct(ProductPO productPO) {
        mongoTemplate.updateFirst(new Query(Criteria.where("id").is(productPO.getId())), Update.update("productCount", productPO.getProductCount()), ProductPO.class);
    }
}
