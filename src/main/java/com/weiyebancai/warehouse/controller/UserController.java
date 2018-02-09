package com.weiyebancai.warehouse.controller;

import com.weiyebancai.warehouse.pagemodel.BaseResult;
import com.weiyebancai.warehouse.pagemodel.DataResult;
import com.weiyebancai.warehouse.pagemodel.Page;
import com.weiyebancai.warehouse.pojo.*;
import com.weiyebancai.warehouse.service.UserServer;
import com.weiyebancai.warehouse.utile.GetObjectUtil;
import com.weiyebancai.warehouse.utile.ResultUtil;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户controller
 *
 * @author caohao 2018/2/2
 */
@RestController
@RequestMapping("/")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServer userServer;

    /**
     * 首页
     */
    @RequestMapping(value = "")
    public ModelAndView index() {
        return new ModelAndView("redirect:login/index.html");
    }

    /**
     * 登陆
     *
     * @param userDTO
     * @param session
     * @return
     */
    @RequestMapping(value = "/userLogin")
    public Result userLogin(UserDTO userDTO, HttpSession session) {
        Assert.notNull(userDTO, "DTO不能为null");
        Assert.notNull(userDTO.getAccount(), "账号不能为null");
        Assert.notNull(userDTO.getPassword(), "密码不能为null");
        UserVO vo = userServer.userLogin(userDTO);
        if (vo != null) {
            session.setAttribute("user", vo);
            return ResultUtil.success();
        }
        return ResultUtil.error(100, "用户名密码错误", null);
    }

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
        Object obj = session.getAttribute("user");
        userServer.insertProduct(productDTO, (UserVO) obj);
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
        DataResult<List<ProductPO>> dataResult = userServer.selectProduct(productSelectDTO);
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
        DataResult<ProductRecordVO> dataResult = userServer.selectRecord(productReocrdDTO);
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
        userServer.insertRecord(productId, recordCount, remark, username);
        return new BaseResult();
    }

    /**
     * 退出
     *
     * @param userDTO
     * @param session
     * @return
     */
    @RequestMapping(value = "/exit")
    public ModelAndView userExit(UserDTO userDTO, HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:login/index.html");
    }
}