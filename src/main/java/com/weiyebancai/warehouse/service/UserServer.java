package com.weiyebancai.warehouse.service;

import com.weiyebancai.warehouse.dao.UserDao;
import com.weiyebancai.warehouse.pojo.UserDTO;
import com.weiyebancai.warehouse.pojo.UserPO;
import com.weiyebancai.warehouse.pojo.UserVO;
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
