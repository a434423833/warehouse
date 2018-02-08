package com.weiyebancai.warehouse.service;

import com.weiyebancai.warehouse.dao.UserDao;
import com.weiyebancai.warehouse.pagemodel.DataResult;
import com.weiyebancai.warehouse.pagemodel.Page;
import com.weiyebancai.warehouse.pojo.*;
import com.weiyebancai.warehouse.pojo.enumclass.UserEnum;
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

    /**
     * 添加商品
     *
     * @param productDTO
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertProduct(ProductDTO productDTO, UserVO userVO) {
        ProductPO productPO = new ProductPO();
        BeanUtils.copyProperties(productDTO, productPO);
        userDao.insertProduct(productPO);
        RecordPO po = new RecordPO();
        po.setProductId(productPO.getId());
        po.setIncident("手动操作添加");
        po.setTime(new Date());
        po.setRecordCount(productPO.getProductCount());
        po.setUserName(userVO.getUsername());
        po.setBeginCount(0);
        po.setEndCount(po.getRecordCount());
        po.setRemark("[管理员]手动添加");
        userDao.insertRecord(po);

    }

    /**
     * 查找商品
     *
     * @param productSelectDTO
     * @return
     */
    public DataResult<List<ProductPO>> selectProduct(ProductSelectDTO productSelectDTO) {
        return userDao.selectProduct(productSelectDTO);
    }

    /**
     * 查找商品库存变更记录
     *
     * @param productReocrdDTO
     * @return
     */
    public DataResult<List<RecordPO>> selectRecord(ProductReocrdDTO productReocrdDTO) {
        return userDao.selectRecord(productReocrdDTO);
    }

    /**
     * 添加商品库存变更记录
     *
     * @param productId
     * @param recordCount
     * @param remark
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertRecord(String productId, Integer recordCount, String remark, String username) {
        ProductPO productPO = userDao.findOneProduct(productId);
        RecordPO po = new RecordPO();
        po.setProductId(productId);
        po.setRemark(remark);
        po.setBeginCount(productPO.getProductCount());
        po.setRecordCount(recordCount);
        po.setEndCount(productPO.getProductCount() + recordCount);
        po.setTime(new Date());
        po.setIncident("手动操作修改");
        po.setUserName(username);
        userDao.insertRecord(po);
        productPO.setProductCount(productPO.getProductCount() + recordCount);
        userDao.updateProduct(productPO);
    }
}
