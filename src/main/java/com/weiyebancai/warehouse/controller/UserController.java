package com.weiyebancai.warehouse.controller;

import com.weiyebancai.warehouse.pagemodel.BaseResult;
import com.weiyebancai.warehouse.pagemodel.DataResult;
import com.weiyebancai.warehouse.pagemodel.Page;
import com.weiyebancai.warehouse.pojo.*;
import com.weiyebancai.warehouse.service.ProductServer;
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
    @Autowired
    private ProductServer productServer;

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
    public Result userLogin(@RequestBody UserDTO userDTO, HttpSession session) {
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