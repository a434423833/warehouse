package com.weiyebancai.warehouse.controller;

import com.weiyebancai.warehouse.pagemodel.BaseResult;
import com.weiyebancai.warehouse.pagemodel.DataResult;
import com.weiyebancai.warehouse.pojo.OrderPO;
import com.weiyebancai.warehouse.pojo.ProductPO;
import com.weiyebancai.warehouse.pojo.ProductToOrderDTO;
import com.weiyebancai.warehouse.pojo.SelectOrderDTO;
import com.weiyebancai.warehouse.service.OrderServer;
import com.weiyebancai.warehouse.utile.GetObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 查询订单
     *
     * @param selectOrderDTO
     * @return
     */
    @RequestMapping(value = "/selectOrder", method = RequestMethod.POST)
    public BaseResult selectOrder(@RequestBody SelectOrderDTO selectOrderDTO) {
        DataResult<List<OrderPO>> orderPOList = orderServer.selectOrder(selectOrderDTO);
        return orderPOList;
    }

    /**
     * 订单添加商品
     *
     * @param productToOrderDTO
     * @return
     */
    @RequestMapping(value = "/addProductToOrder", method = RequestMethod.POST)
    public BaseResult addProductToOrder(@RequestBody ProductToOrderDTO productToOrderDTO) {
        orderServer.addProductToOrder(productToOrderDTO);
        return new BaseResult();
    }

    /**
     * 删除订单
     *
     * @param noteno
     * @return
     */
    @RequestMapping(value = "/delectOrder", method = RequestMethod.POST)
    public BaseResult delectOrder(Long noteno) {
        Assert.notNull(noteno, "订单编号不能为null");
        orderServer.delectOrder(noteno);
        return new BaseResult();
    }

    /**
     * 修改订单数量
     *
     * @param orderPO
     * @return
     */
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    public BaseResult updateOrder(@RequestBody OrderPO orderPO) {
        List<ProductPO> productList = orderPO.getProductList();
        orderServer.updateOrder(orderPO);
        return new BaseResult();
    }
}
