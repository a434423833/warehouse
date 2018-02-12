package com.weiyebancai.warehouse.controller;

import com.weiyebancai.warehouse.pagemodel.BaseResult;
import com.weiyebancai.warehouse.service.OrderServer;
import com.weiyebancai.warehouse.utile.GetObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 出库单控制层
 *
 * @author caohao 2018/2/12
 */
@RestController
@RequestMapping("/")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderServer orderServer;

    /**
     * 创建订单
     *
     * @param remork
     * @return
     */
    @RequestMapping(value = "/createOrder", method = RequestMethod.GET)
    public BaseResult createOutStock(@RequestParam("remork") String remork, HttpSession session) {
        Assert.notNull(remork, "备注不能为null");
        String username = GetObjectUtil.getUserName(session);
        orderServer.createOrder(remork, username);
        return new BaseResult();
    }
}
