package com.weiyebancai.warehouse.controller;

import com.weiyebancai.warehouse.pojo.Result;
import com.weiyebancai.warehouse.pojo.UserDTO;
import com.weiyebancai.warehouse.pojo.UserVO;
import com.weiyebancai.warehouse.service.UserServer;
import com.weiyebancai.warehouse.utile.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

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
        return new ModelAndView("redirect:login/index_副本.html");
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
     * 退出
     *
     * @param userDTO
     * @param session
     * @return
     */
    @RequestMapping(value = "/exit")
    public void userExit(UserDTO userDTO, HttpSession session) {
        session.invalidate();
    }
}