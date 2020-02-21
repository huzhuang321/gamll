package com.gmall.manage.service.impl;

import com.gmall.bean.PmsSkuAttrValue;
import com.gmall.bean.PmsSkuImage;
import com.gmall.bean.PmsSkuInfo;
import com.gmall.bean.PmsSkuSaleAttrValue;
import com.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.gmall.manage.mapper.PmsSkuImageMapper;
import com.gmall.manage.mapper.PmsSkuInfoMapper;
import com.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.gmall.manage.service.SkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class SkuServiceImpl implements SkuService {
    //打印日志
    Logger logger = LoggerFactory.getLogger(SkuServiceImpl.class);
    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;

    /**
     * 根据三级分类查询所有的商品sku信息
     *
     * @param catalog3Id
     * @return
     */
    @Override
    public List<PmsSkuInfo> skuList(String catalog3Id) {
        List<PmsSkuInfo> pmsSkuInfos = null;
        try {
            pmsSkuInfos = pmsSkuInfoMapper.selectAll();
            for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
                String skuId = pmsSkuInfo.getId();
                PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
                PmsSkuImage pmsSkuImage = new PmsSkuImage();
                pmsSkuImage.setSkuId(skuId);
                List<PmsSkuImage> pmsSkuImageList = pmsSkuImageMapper.select(pmsSkuImage);
                pmsSkuInfo.setSkuImageList(pmsSkuImageList);
                pmsSkuAttrValue.setSkuId(skuId);
                List<PmsSkuAttrValue> select = pmsSkuAttrValueMapper.select(pmsSkuAttrValue);
                pmsSkuInfo.setSkuAttrValueList(select);
                PmsSkuSaleAttrValue pmsSkuSaleAttrValue = new PmsSkuSaleAttrValue();
                pmsSkuSaleAttrValue.setSkuId(skuId);
                List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList = pmsSkuSaleAttrValueMapper.select(pmsSkuSaleAttrValue);
                pmsSkuInfo.setSkuSaleAttrValueList(pmsSkuSaleAttrValueList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("skuList", pmsSkuInfoMapper.selectAll());
        }
        return pmsSkuInfos;
    }

    /**
     * 添加商品sku信息
     *
     * @param pmsSkuInfo
     */
    @Transactional
    @Override
    public void SavePmsSkuInfo(PmsSkuInfo pmsSkuInfo) {
        //插入sku商品信息
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        //获取商品sku平台属性值信息
        List<PmsSkuAttrValue> pmsSkuAttrValues = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuAttrValues) {
            pmsSkuAttrValue.setSkuId(pmsSkuInfo.getId());
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);

        }
        //获取商品skusku销售属性值
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValues) {
            pmsSkuSaleAttrValue.setSkuId(pmsSkuInfo.getId());
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }
        // 插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(pmsSkuInfo.getId());
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }
    }

}
