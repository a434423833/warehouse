package com.weiyebancai.warehouse.service;

import com.weiyebancai.warehouse.dao.OrderDao;
import com.weiyebancai.warehouse.pojo.OrderPO;
import com.weiyebancai.warehouse.pojo.enumclass.OutStockEnum;
import com.weiyebancai.warehouse.utile.UUidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 出库单server
 *
 * @author caohao 2018/2/2
 */
@Service
public class OrderServer {
    @Autowired
    private OrderDao outStockDao;

    /**
     * 生成订单
     *
     * @param remork
     * @param username
     */
    public void createOrder(String remork, String username) {
        OrderPO orderPO = new OrderPO();
        orderPO.setNoteno(UUidUtil.getUUidInt());
        orderPO.setCreateTime(new Date());
        orderPO.setCreateUser(username);
        orderPO.setRemork(remork);
        orderPO.setState(OutStockEnum.ONE);
        outStockDao.insertOrder(orderPO);
    }
}
