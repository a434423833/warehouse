package com.weiyebancai.warehouse.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.weiyebancai.warehouse.pagemodel.DataResult;
import com.weiyebancai.warehouse.pagemodel.Page;
import com.weiyebancai.warehouse.pojo.OrderPO;
import com.weiyebancai.warehouse.pojo.ProductPO;
import com.weiyebancai.warehouse.pojo.ProductToOrderDTO;
import com.weiyebancai.warehouse.pojo.SelectOrderDTO;
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

    public DataResult<List<OrderPO>> selectOrder(SelectOrderDTO selectOrderDTO) {
        DataResult<List<OrderPO>> dataResult = new DataResult();
        Page page = selectOrderDTO.getPage();
        DBObject dbObject = new BasicDBObject();
        BasicDBObject fieldsObject = new BasicDBObject();
        fieldsObject.put("_id", true);
        fieldsObject.put("noteno", true);
        fieldsObject.put("stockUser", true);
        fieldsObject.put("remork", true);
        fieldsObject.put("createTime", true);
        fieldsObject.put("createUser", true);
        fieldsObject.put("updateTime", true);
        fieldsObject.put("productList", true);
        fieldsObject.put("state", true);
        Query query = new BasicQuery(dbObject, fieldsObject);
        if (selectOrderDTO.getNoteno() != null) {
            query.addCriteria(Criteria.where("noteno").is(selectOrderDTO.getNoteno()));
        }
        if (selectOrderDTO.getRemork() != null) {
            query.addCriteria(Criteria.where("remork").regex(".*?" + selectOrderDTO.getRemork() + ".*"));
        }
        if (selectOrderDTO.getOrderState() != null) {
            query.addCriteria(Criteria.where("state").is(selectOrderDTO.getOrderState()));
        }
        //必传两段时间
        if (selectOrderDTO.getBeginTime() != null && selectOrderDTO.getEndTime() != null) {
            query.addCriteria(Criteria.where("createTime").gte(selectOrderDTO.getBeginTime()).lte(selectOrderDTO.getEndTime()));
        }
        //ne、neq不相等，gt大于， lt小于 gte、ge大于等于
        // lte、le 小于等于   not非   mod求模   is [not] div by是否能被某数整除   is [not] even是否为偶数
        if (selectOrderDTO.getBeginMoney() != null) {
            query.addCriteria(Criteria.where("money").gte((selectOrderDTO.getBeginMoney())));
        }
        if (selectOrderDTO.getEndMoney() != null) {
            query.addCriteria(Criteria.where("money").lte(selectOrderDTO.getEndMoney()));
        }
        if (selectOrderDTO.getBeginMoney() != null && selectOrderDTO.getEndMoney() != null) {
            query.addCriteria(Criteria.where("money").gte(selectOrderDTO.getBeginMoney()).lte(selectOrderDTO.getEndMoney()));
        }
        if (page.getOrderBy() != null) {
            query.with(new Sort(new Sort.Order(page.getOrderBy().equals("asc") ? Sort.Direction.DESC : Sort.Direction.ASC, "createTime")));
            query.with(new Sort(new Sort.Order(page.getOrderBy().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "state")));
        }
        //分页
        query.skip((page.getPageNum() - 1) * page.getPageSize());
        query.limit(page.getPageSize());
        List<OrderPO> list = this.mongoTemplate.find(query, OrderPO.class);
        long count = this.mongoTemplate.count(query, OrderPO.class);
        page.setTotal(count);
        page.setPages(count % page.getPageSize() == 0 ? (int) count / page.getPageSize() : (int) count / page.getPageSize() + 1);
        dataResult.setPage(page);
        dataResult.setData(list);
        return dataResult;
    }

    /**
     * 根据noteno查找订单
     *
     * @param orderId
     * @return
     */
    public OrderPO selectOrderProduct(Long orderId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("noteno").is(orderId));
        List<OrderPO> orderPOList = this.mongoTemplate.find(query, OrderPO.class);
        if (orderPOList == null || orderPOList.size() == 0) {
            throw new RuntimeException("找不到订单");
        }
        return orderPOList.get(0);
    }

    /**
     * 订单商品修改
     *
     * @param orderId
     * @param productPOList
     */
    public void updateOrderProduct(Long orderId, List<ProductPO> productPOList) {
        Update update = new Update();
        update.set("productList", productPOList);
        update.set("updateTime", new Date());
        mongoTemplate.updateFirst(new Query(Criteria.where("noteno").is(orderId)), update, OrderPO.class);
    }

    /**
     * 根据noteno删除订单
     *
     * @param noteno
     */
    public void deleteOrder(Long noteno) {
        mongoTemplate.remove(new Query(Criteria.where("noteno").is(noteno)), OrderPO.class);
    }

    /**
     * 修改订单
     *
     * @param orderPO
     */
    public void updatOrder(OrderPO orderPO) {
        Update update = new Update();
        orderPO.setUpdateTime(new Date());
        update.set("productList", orderPO.getProductList());
        update.set("updateTime", orderPO.getUpdateTime());
        mongoTemplate.updateFirst(new Query(Criteria.where("noteno").is(orderPO.getNoteno())), update, OrderPO.class);
    }

    /**
     * 修改订单状态
     */
    public void updateOrderState(Long noteno, String state, String stockUser) {
        OrderPO orderPO = new OrderPO();
        Update update = new Update();
        orderPO.setUpdateTime(new Date());
        if (stockUser != null) {
            update.set("stockUser", stockUser);
        }
        update.set("state", state);
        update.set("updateTime", orderPO.getUpdateTime());
        mongoTemplate.updateFirst(new Query(Criteria.where("noteno").is(noteno)), update, OrderPO.class);
    }
}
