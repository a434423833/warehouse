package com.weiyebancai.warehouse.service;

import com.weiyebancai.warehouse.dao.OrderDao;
import com.weiyebancai.warehouse.dao.ProductDao;
import com.weiyebancai.warehouse.pagemodel.DataResult;
import com.weiyebancai.warehouse.pojo.OrderPO;
import com.weiyebancai.warehouse.pojo.ProductPO;
import com.weiyebancai.warehouse.pojo.ProductToOrderDTO;
import com.weiyebancai.warehouse.pojo.RecordPO;
import com.weiyebancai.warehouse.pojo.SelectOrderDTO;
import com.weiyebancai.warehouse.pojo.enumclass.OutStockEnum;
import com.weiyebancai.warehouse.utile.UUidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 出库单server
 *
 * @author caohao 2018/2/2
 */
@Service
public class OrderServer {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

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
        orderPO.setStockUser("");
        orderPO.setProductList(new ArrayList<>());
        orderDao.insertOrder(orderPO);
    }

    /**
     * 查询订单
     *
     * @param selectOrderDTO
     */
    public DataResult<List<OrderPO>> selectOrder(SelectOrderDTO selectOrderDTO) {
        return orderDao.selectOrder(selectOrderDTO);
    }

    /**
     * 订单添加商品
     *
     * @param productToOrderDTO
     * @return
     */
    public void addProductToOrder(ProductToOrderDTO productToOrderDTO) {
        List<ProductPO> productPOList = orderDao.selectOrderProduct(productToOrderDTO.getOrderId()).getProductList();
        //判断订单中是否含有添加商品
        Optional<ProductPO> productPOS = productPOList.stream().filter(p -> p.getId().equals(productToOrderDTO.getProductId())).findFirst();
        if (!productPOS.isPresent()) {
            //不存在此商品
            ProductPO productPO = productDao.selectProductById(productToOrderDTO.getProductId());
            productPO.setProductCount(productToOrderDTO.getProductRecord());
            productPOList.add(productPO);
        } else {
            //存在此商品
            ProductPO productPO = productPOS.get();
            //由于是浅复制，所以直接更改该po就改了list中对应po值
            productPO.setProductCount(productPO.getProductCount() + productToOrderDTO.getProductRecord());
        }
        orderDao.updateOrderProduct(productToOrderDTO.getOrderId(), productPOList);
    }

    /**
     * 删除订单
     *
     * @param noteno
     */
    public void delectOrder(Long noteno) {
        OrderPO orderPO = orderDao.selectOrderProduct(noteno);
        if (!orderPO.getState().equals("待确认") && !orderPO.getState().equals("待出库")) {
            throw new RuntimeException("订单状态无法删除");
        }
        orderDao.deleteOrder(noteno);
    }

    /**
     * 修改订单
     *
     * @param orderPO
     */
    public void updateOrder(OrderPO orderPO) {
        //判断库存数量
        List<ProductPO> productList = orderPO.getProductList();
        List<String> list = productList.stream().map(ProductPO::getId).collect(Collectors.toList());
        List<ProductPO> warehouseProductList = productDao.findProductById(list);
        Map<String, List<ProductPO>> warehouseProductMap = warehouseProductList.stream().collect(Collectors.groupingBy(ProductPO::getId));
        for (ProductPO productPO : productList) {
            Integer warehouseCount = warehouseProductMap.get(productPO.getId()).get(0).getProductCount();
            if (productPO.getProductCount() > warehouseCount) {
                throw new RuntimeException(productPO.getProductName() + "库存不足,只有" + warehouseCount + "个");
            }
        }
        orderDao.updatOrder(orderPO);
    }

    /**
     * 确认订单
     *
     * @param noteno
     * @param state
     */
    public DataResult<OrderPO> updateOrderState(Long noteno, String state) {
        orderDao.updateOrderState(noteno, state, null);
        OrderPO orderPO = orderDao.selectOrderProduct(noteno);
        return new DataResult<>(orderPO);
    }

    /**
     * 出库操作
     *
     * @param orderPO
     */
    public DataResult<OrderPO> stock(OrderPO orderPO, String userName) {
        List<ProductPO> productList = orderPO.getProductList();
        List<String> list = productList.stream().map(ProductPO::getId).collect(Collectors.toList());
        List<ProductPO> warehouseProductList = productDao.findProductById(list);
        Map<String, List<ProductPO>> warehouseProductMap = warehouseProductList.stream().collect(Collectors.groupingBy(ProductPO::getId));
        for (ProductPO productPO : productList) {
            Integer warehouseCount = warehouseProductMap.get(productPO.getId()).get(0).getProductCount();
            if (productPO.getProductCount() > warehouseCount) {
                throw new RuntimeException(productPO.getProductName() + "库存不足,只有" + warehouseCount + "个");
            }
        }
        //库存足够,1,修改状态,2修改库存数量
        orderDao.updateOrderState(orderPO.getNoteno(), "已出库", userName);
        //得到库存PO和变更PO集合
        List<RecordPO> recordPOList = new ArrayList<>();
        List<ProductPO> productPOList = new ArrayList<>();
        for (ProductPO productPO : orderPO.getProductList()) {
            Integer warehouseCount = warehouseProductMap.get(productPO.getId()).get(0).getProductCount();
            RecordPO po = new RecordPO();
            po.setProductId(productPO.getId());
            po.setIncident("[订单扣减]");
            po.setTime(new Date());
            po.setUserName(userName);
            po.setBeginCount(warehouseCount);
            po.setRecordCount(0 - productPO.getProductCount());
            po.setEndCount(warehouseCount - productPO.getProductCount());
            po.setRemark("订单备注:" + orderPO.getRemork());
            recordPOList.add(po);
            productPO.setProductCount(warehouseCount - productPO.getProductCount());
            productPOList.add(productPO);
        }
        //修改库存
        //添加库存变记录
        productDao.updateProduct(productPOList);
        productDao.insertRecord(recordPOList);
        //查询新订单数据
        OrderPO newOrderPO = orderDao.selectOrderProduct(orderPO.getNoteno());
        return new DataResult<>(newOrderPO);
    }
}
