package com.weiyebancai.warehouse.service;

import com.weiyebancai.warehouse.dao.ProductDao;
import com.weiyebancai.warehouse.dao.UserDao;
import com.weiyebancai.warehouse.pagemodel.DataResult;
import com.weiyebancai.warehouse.pojo.ProductDTO;
import com.weiyebancai.warehouse.pojo.ProductPO;
import com.weiyebancai.warehouse.pojo.ProductRecordVO;
import com.weiyebancai.warehouse.pojo.ProductReocrdDTO;
import com.weiyebancai.warehouse.pojo.ProductSelectDTO;
import com.weiyebancai.warehouse.pojo.RecordPO;
import com.weiyebancai.warehouse.pojo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 商品以及库存变更server
 *
 * @author caohao 2018/2/2
 */
@Service
public class ProductServer {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;

    /**
     * 添加商品
     *
     * @param productDTO
     */
    public void insertProduct(ProductDTO productDTO, UserVO userVO) {
        ProductPO productPO = new ProductPO();
        BeanUtils.copyProperties(productDTO, productPO);
        productPO.setUpdateTime(new Date());
        productDao.insertProduct(productPO);
        RecordPO po = new RecordPO();
        po.setProductId(productPO.getId());
        po.setIncident("手动操作添加");
        po.setTime(new Date());
        po.setRecordCount(productPO.getProductCount());
        po.setUserName(userVO.getUsername());
        po.setBeginCount(0);
        po.setEndCount(po.getRecordCount());
        po.setRemark("[管理员]手动添加");
        productDao.insertRecord(po);

    }

    /**
     * 查找商品
     *
     * @param productSelectDTO
     * @return
     */
    public DataResult<List<ProductPO>> selectProduct(ProductSelectDTO productSelectDTO) {
        return productDao.selectProduct(productSelectDTO);
    }

    /**
     * 查找商品库存变更记录
     *
     * @param productReocrdDTO
     * @return
     */
    public DataResult<ProductRecordVO> selectRecord(ProductReocrdDTO productReocrdDTO) {
        ProductRecordVO productRecordVO = new ProductRecordVO();
        ProductPO po = productDao.findOneProduct(productReocrdDTO.getProductId());
        productRecordVO.setProductId(po.getId());
        productRecordVO.setProductName(po.getProductName());
        productRecordVO.setProductWarehouse(po.getProductWarehouse());
        DataResult<List<RecordPO>> dataResult = productDao.selectRecord(productReocrdDTO);
        productRecordVO.setList(dataResult.getData());
        return new DataResult<ProductRecordVO>(productRecordVO, dataResult.getPage());
    }

    /**
     * 添加库存变更记录以及修改库存
     *
     * @param productId
     * @param recordCount
     * @param remark
     */
    public void insertRecord(String productId, Integer recordCount, String remark, String username) {
        ProductPO productPO = productDao.findOneProduct(productId);
        if (recordCount < 0 && productPO.getProductCount() + recordCount < 0 ? true : false) {
            throw new RuntimeException("减少库存数不能超过当前的库存");
        }
        RecordPO po = new RecordPO();
        po.setProductId(productId);
        po.setRemark(remark);
        po.setBeginCount(productPO.getProductCount());
        po.setRecordCount(recordCount);
        po.setEndCount(productPO.getProductCount() + recordCount);
        po.setTime(new Date());
        po.setIncident("手动操作修改");
        po.setUserName(username);
        productDao.insertRecord(po);
        productPO.setProductCount(productPO.getProductCount() + recordCount);
        productDao.updateProduct(productPO);
    }
}
