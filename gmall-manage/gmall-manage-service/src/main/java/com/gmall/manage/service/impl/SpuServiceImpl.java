package com.gmall.manage.service.impl;

import com.gmall.bean.PmsProductImage;
import com.gmall.bean.PmsProductInfo;
import com.gmall.bean.PmsProductSaleAttr;
import com.gmall.bean.PmsProductSaleAttrValue;
import com.gmall.manage.mapper.PmsProductImageMapper;
import com.gmall.manage.mapper.PmsProductInfoMapper;
import com.gmall.manage.mapper.PmsProductSaleAttrMapper;
import com.gmall.manage.mapper.PmsProductSaleAttrValueMapper;
import com.gmall.manage.service.SpuService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {
    Logger logger = LoggerFactory.getLogger(SpuServiceImpl.class);
    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    private PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Autowired
    private PmsProductImageMapper pmsProductImageMapper;

    /**
     * 查询所有商品spu属性
     *
     * @param catalog3Id
     * @return
     */
    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo record = new PmsProductInfo();
        record.setCatalog3Id(catalog3Id);
        //商品spu属性
        List<PmsProductInfo> pmsProductInfos = pmsProductInfoMapper.select(record);
        //商品spu属性值
        for (PmsProductInfo pmsProductInfo : pmsProductInfos) {
            PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
            pmsProductSaleAttr.setProductId(pmsProductInfo.getId());
            List<PmsProductSaleAttr> pmsProductSaleAttrList = pmsProductSaleAttrMapper.select(pmsProductSaleAttr);
            pmsProductInfo.setSpuSaleAttrList(pmsProductSaleAttrList);
            //获取销售属性值

        }
        //商品spu图片信息
        for (PmsProductInfo pmsProductInfo : pmsProductInfos) {
            PmsProductImage pmsProductImage = new PmsProductImage();
            pmsProductImage.setProductId(pmsProductInfo.getId());
            List<PmsProductImage> pmsProductImageList = pmsProductImageMapper.select(pmsProductImage);
            pmsProductInfo.setSpuImageList(pmsProductImageList);
        }

        return pmsProductInfos;
    }

    /**
     * 根据商品productId获取商品销售属性
     *
     * @param productId
     * @return
     */
    @Override
    public List<PmsProductSaleAttr> spuSaleAttr(String productId) {
        PmsProductSaleAttr record = new PmsProductSaleAttr();
        record.setProductId(productId);
        //获取销售属性
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.select(record);
        //获取销售属性值
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductSaleAttrs) {
            PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
            pmsProductSaleAttrValue.setSaleAttrId(pmsProductSaleAttr.getSaleAttrId());
            pmsProductSaleAttrValue.setProductId(pmsProductSaleAttr.getProductId());
            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.select(pmsProductSaleAttrValue);
            pmsProductSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
        }
        return pmsProductSaleAttrs;
    }


    /**
     * 查询销售属性值
     *
     * @param pmsProductSaleAttr
     * @return
     */
    @Override
    public List<PmsProductSaleAttrValue> spuSaleAttrValue(PmsProductSaleAttr pmsProductSaleAttr) {
        PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
        pmsProductSaleAttrValue.setProductId(pmsProductSaleAttr.getProductId());
        pmsProductSaleAttrValue.setSaleAttrId(pmsProductSaleAttr.getSaleAttrId());
        List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.select(pmsProductSaleAttrValue);
        return pmsProductSaleAttrValues;
    }

    /**
     * 添加spu商品信息
     *
     * @param pmsProductInfo
     * @return
     */
    @Override
    @Transactional
    public void savePmsProductInfo(PmsProductInfo pmsProductInfo) {
        try {
            //判断商品spu的ID是否为空，为空，新增
            if (StringUtils.isEmpty(pmsProductInfo.getId())) {
                //新增商品spu相关信息
                saveOrUpdatePmsProductInfo(pmsProductInfo);
            } else {
                //不为空，则修改
                Example example = new Example(PmsProductInfo.class);
                example.createCriteria().andEqualTo("id", pmsProductInfo.getId());
                //更新商品spu信息
                pmsProductInfoMapper.updateByExample(pmsProductInfo, example);
                //删除相关spu商品属性，属性值，图片存储信息
                delSpuInfo(pmsProductInfo.getId());
                //修改商品spu相关信息
                saveOrUpdatePmsProductInfo(pmsProductInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("添加spu商品信息", pmsProductInfoMapper.insertSelective(pmsProductInfo));
        }

    }

    /**
     * 删除spu商品信息
     *
     * @param spuId
     */
    @Override
    public void delSpuInfo(String spuId) {
        try {
            PmsProductInfo record1 = new PmsProductInfo();
            record1.setId(spuId);
            //删除商品的图片地址信息
            PmsProductImage pmsProductImage = new PmsProductImage();
            pmsProductImage.setProductId(spuId);
            for (PmsProductImage image : pmsProductImageMapper.select(pmsProductImage)) {
                image.setProductId(spuId);
                pmsProductImageMapper.delete(image);
            }
            PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
            pmsProductSaleAttr.setProductId(spuId);
            //删除商品的销售属性
            for (PmsProductSaleAttr saleAttr : pmsProductSaleAttrMapper.select(pmsProductSaleAttr)) {
                pmsProductSaleAttr.setProductId(spuId);
                pmsProductSaleAttrMapper.delete(pmsProductSaleAttr);
            }
            //删除销售属性值
            PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
            pmsProductSaleAttrValue.setProductId(spuId);
            for (PmsProductSaleAttrValue saleAttrValue : pmsProductSaleAttrValueMapper.select(pmsProductSaleAttrValue)) {
                saleAttrValue.setProductId(spuId);
                pmsProductSaleAttrValueMapper.delete(saleAttrValue);
            }
            //删除商品SPu信息
            pmsProductInfoMapper.delete(record1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("删除失败", "delSpuInfo");
        }


    }

    /**
     * 查询商品spu图片信息
     *
     * @param productId
     * @return
     */
    @Override
    public List<PmsProductImage> spuImageList(String productId) {
        PmsProductImage pmsProductImage = new PmsProductImage();
        pmsProductImage.setProductId(productId);
        return this.pmsProductImageMapper.select(pmsProductImage);
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId, String skuId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.selectSpuSaleAttrListCheckBySku(productId, skuId);
        return pmsProductSaleAttrs;
    }

    //---------------------新增或修改商品spu信息提取类------------------------

    public void saveOrUpdatePmsProductInfo(PmsProductInfo pmsProductInfo) {
        //插入商品spu信息
        pmsProductInfoMapper.insertSelective(pmsProductInfo);
        List<PmsProductSaleAttr> pmsProductSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
        //插入商品图片信息地址
        for (PmsProductImage pmsProductImage : pmsProductInfo.getSpuImageList()) {
            pmsProductImage.setProductId(pmsProductInfo.getId());
            pmsProductImageMapper.insertSelective(pmsProductImage);
        }
        //插入商品销售属性信息
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductSaleAttrList) {
            pmsProductSaleAttr.setProductId(pmsProductInfo.getId());
            pmsProductSaleAttrMapper.insertSelective(pmsProductSaleAttr);
            //插入商品销售属性值信息
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : pmsProductSaleAttr.getSpuSaleAttrValueList()) {
                pmsProductSaleAttrValue.setProductId(pmsProductInfo.getId());
                pmsProductSaleAttrValue.setSaleAttrId(pmsProductSaleAttr.getSaleAttrId());
                pmsProductSaleAttrValueMapper.insertSelective(pmsProductSaleAttrValue);
            }
        }
    }
}
