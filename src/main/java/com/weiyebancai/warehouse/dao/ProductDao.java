package com.weiyebancai.warehouse.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.weiyebancai.warehouse.pagemodel.DataResult;
import com.weiyebancai.warehouse.pagemodel.Page;
import com.weiyebancai.warehouse.pojo.ProductPO;
import com.weiyebancai.warehouse.pojo.ProductReocrdDTO;
import com.weiyebancai.warehouse.pojo.ProductSelectDTO;
import com.weiyebancai.warehouse.pojo.RecordPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 商品操作mongodb
 *
 * @author caohao 2018/2/12
 */
@Component
public class ProductDao {
    @Autowired
    private MongoTemplate mongoTemplate;

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
        po.setTime(new Date());
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
        DBObject dbObject = new BasicDBObject();
        BasicDBObject fieldsObject = new BasicDBObject();
        fieldsObject.put("_id", true);
        fieldsObject.put("productName", true);
        fieldsObject.put("productCategory", true);
        fieldsObject.put("productCount", true);
        fieldsObject.put("productWarehouse", true);
        fieldsObject.put("productBrand", true);
        fieldsObject.put("updateTime", true);
        Query query = new BasicQuery(dbObject, fieldsObject);
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
            query.with(new Sort(new Sort.Order(page.getOrderBy().equals("asc") ? Sort.Direction.DESC : Sort.Direction.ASC, "updateTime")));
            query.with(new Sort(new Sort.Order(page.getOrderBy().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productCategory")));
            query.with(new Sort(new Sort.Order(page.getOrderBy().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productBrand")));
            query.with(new Sort(new Sort.Order(page.getOrderBy().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productName")));
            query.with(new Sort(new Sort.Order(page.getOrderBy().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productWarehouse")));
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
     * 修改库存商品库存
     *
     * @param productPO
     */
    public void updateProduct(ProductPO productPO) {
        Update update = new Update();
        productPO.setUpdateTime(new Date());
        update.set("productCount", productPO.getProductCount());
        update.set("updateTime", productPO.getUpdateTime());
        mongoTemplate.updateFirst(new Query(Criteria.where("id").is(productPO.getId())), update, ProductPO.class);
    }

    /**
     * 删除商品
     *
     * @param productId
     */
    public void delectProduct(String productId) {
        mongoTemplate.remove(new Query(Criteria.where("id").is(productId)), ProductPO.class);
    }

    /**
     * 删除变更记录
     *
     * @param productId
     */
    public void delectRecord(String productId) {
        mongoTemplate.remove(new Query(Criteria.where("productId").is(productId)), RecordPO.class);
    }
}
