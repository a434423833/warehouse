package com.weiyebancai.warehouse.controller;

import com.weiyebancai.warehouse.pagemodel.BaseResult;
import com.weiyebancai.warehouse.pagemodel.DataResult;
import com.weiyebancai.warehouse.pojo.ProductDTO;
import com.weiyebancai.warehouse.pojo.ProductPO;
import com.weiyebancai.warehouse.pojo.ProductRecordVO;
import com.weiyebancai.warehouse.pojo.ProductReocrdDTO;
import com.weiyebancai.warehouse.pojo.ProductSelectDTO;
import com.weiyebancai.warehouse.pojo.Result;
import com.weiyebancai.warehouse.pojo.UserVO;
import com.weiyebancai.warehouse.service.ProductServer;
import com.weiyebancai.warehouse.service.UserServer;
import com.weiyebancai.warehouse.utile.GetObjectUtil;
import com.weiyebancai.warehouse.utile.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 商品controller
 *
 * @author caohao 2018/2/2
 */
@RestController
@RequestMapping("/")
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private UserServer userServer;
    @Autowired
    private ProductServer productServer;

    /**
     * 添加商品
     *
     * @param productDTO
     * @param session
     * @return
     */
    @RequestMapping(value = "/insertProduct", method = RequestMethod.POST)
    public Result insertProduct(@RequestBody ProductDTO productDTO, HttpSession session) {
        Assert.notNull(productDTO, "DTO不能为null");
        Assert.notNull(productDTO.getProductName(), "名称不能为null");
        Assert.notNull(productDTO.getProductCategory(), "类别不能为null");
        Assert.notNull(productDTO.getProductCount(), "数量不能为null");
        Assert.notNull(productDTO.getProductWarehouse(), "所在地不能为null");
        Assert.notNull(productDTO.getProductBid(), "进价不能为null");
        Assert.isTrue(productDTO.getProductName().length() < 12, "产品名称太长null");
        Object obj = session.getAttribute("user");
        productServer.insertProduct(productDTO, (UserVO) obj);
        return ResultUtil.success();
    }

    /**
     * 查找商品
     *
     * @param productSelectDTO
     * @return
     */
    @RequestMapping(value = "/selectProduct", method = RequestMethod.POST)
    public BaseResult selectProduct(@RequestBody ProductSelectDTO productSelectDTO) {
        Assert.notNull(productSelectDTO, "DTO为null");
        DataResult<List<ProductPO>> dataResult = productServer.selectProduct(productSelectDTO);
        return dataResult;
    }

    /**
     * 查找商品库存变更记录
     *
     * @param productReocrdDTO
     * @return
     */
    @RequestMapping(value = "/selectRecord", method = RequestMethod.POST)
    public BaseResult selectRecord(@RequestBody ProductReocrdDTO productReocrdDTO) {
        Assert.notNull(productReocrdDTO, "DTO为null");
        Assert.notNull(productReocrdDTO.getProductId(), "商品id不能为null");
        DataResult<ProductRecordVO> dataResult = productServer.selectRecord(productReocrdDTO);
        return dataResult;
    }

    /**
     * 变更库存以及商品库存变更记录
     *
     * @return
     */
    @RequestMapping(value = "/insertRecord", method = RequestMethod.POST)
    public BaseResult insertRecord(String productId, String remark, Integer recordCount, HttpSession session) {
        Assert.notNull(productId, "productId为null");
        Assert.notNull(remark, "备注不能为null");
        Assert.notNull(recordCount, "变更数量不能为null");
        String username = GetObjectUtil.getUserName(session);
        productServer.insertRecord(productId, recordCount, remark, username);
        return new BaseResult();
    }

    /**
     * 删除商品
     * @param productId
     * @return
     */
    @RequestMapping(value = "/delectProduct", method = RequestMethod.POST)
    public BaseResult delectProduct(String productId) {
        Assert.notNull(productId, "productId为null");
        productServer.delectProduct(productId);
        return new BaseResult();
    }

}
