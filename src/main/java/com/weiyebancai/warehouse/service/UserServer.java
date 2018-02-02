package com.weiyebancai.warehouse.service;

import com.weiyebancai.warehouse.dao.UserDao;
import com.weiyebancai.warehouse.pojo.UserDTO;
import com.weiyebancai.warehouse.pojo.UserPO;
import com.weiyebancai.warehouse.utile.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author caohao 2018/2/2
 */
@Service
public class UserServer {
    @Autowired
    private UserDao userDao;

    /**
     * 用户登录
     *
     * @param userDTO
     */
    public boolean userLogin(UserDTO userDTO) {
        String password = Md5.getMD5(userDTO.getPassword());
        UserPO userPO = new UserPO();
        userPO.setAccount(userDTO.getAccount());
        userPO.setPassword(password);
        UserPO po = userDao.findUserByUserName(userPO);
        return po == null ? false : true;
    }
}
