package com.weiyebancai.warehouse.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.weiyebancai.warehouse.pagemodel.DataResult;
import com.weiyebancai.warehouse.pagemodel.Page;
import com.weiyebancai.warehouse.pojo.OrderPO;
import com.weiyebancai.warehouse.pojo.ProductPO;
import com.weiyebancai.warehouse.pojo.SelectOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

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
        fieldsObject.put("remork", true);
        fieldsObject.put("createTime", true);
        fieldsObject.put("createUser", true);
        fieldsObject.put("state", true);
        Query query = new BasicQuery(dbObject, fieldsObject);
        if (selectOrderDTO.getNoteno() != null) {
            query.addCriteria(Criteria.where("noteno").regex(".*?" + selectOrderDTO.getNoteno() + ".*"));
        }
        if (selectOrderDTO.getOrderState() != null) {
            query.addCriteria(Criteria.where("state").regex(".*?" + selectOrderDTO.getOrderState() + ".*"));
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
}
