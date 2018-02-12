package com.weiyebancai.warehouse.service;

import com.weiyebancai.warehouse.dao.ProductDao;
import com.weiyebancai.warehouse.dao.UserDao;
import com.weiyebancai.warehouse.pagemodel.DataResult;
import com.weiyebancai.warehouse.pojo.*;
import com.weiyebancai.warehouse.utile.Md5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author caohao 2018/2/2
 */
@Service
public class UserServer {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;

    /**
     * 用户登录
     *
     * @param userDTO
     */
    public UserVO userLogin(UserDTO userDTO) {
        String password = Md5.getMD5(userDTO.getPassword());
        UserPO userPO = new UserPO();
        userPO.setAccount(userDTO.getAccount());
        userPO.setPassword(password);
        UserPO po = userDao.findUserByUserName(userPO);
        if (po != null) {
            UserVO vo = new UserVO();
            vo.setUsername(po.getUsername());
            vo.setAuth(po.getAuth());
            return vo;
        }
        return null;
    }

}
