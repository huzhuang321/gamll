package com.gmall.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gmall.bean.PmsSkuAttrValue;
import com.gmall.bean.PmsSkuImage;
import com.gmall.bean.PmsSkuInfo;
import com.gmall.bean.PmsSkuSaleAttrValue;
import com.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.gmall.manage.mapper.PmsSkuImageMapper;
import com.gmall.manage.mapper.PmsSkuInfoMapper;
import com.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.gmall.manage.service.SkuService;
import com.gmall.util.RedisUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Random;
import java.util.UUID;

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

    @Autowired
    private RedisUtils redisUtils;

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

    @Override
    public PmsSkuInfo getSkuById(String skuId) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        Jedis jedis = redisUtils.getJedis();//获取jedis对象
        //判断缓存是否存在商品详情
        String skuKey = "sku:" + skuId + ":info";
        String skuJson = jedis.get(skuKey);
        if (StringUtils.isNotBlank(skuJson)) {
            //缓存中读取数据
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        } else {
            //从数据库中读取数据
            //设置分布式锁
            String token = UUID.randomUUID().toString();
            // 拿到锁的线程有10秒的过期时间
            String OK = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10 * 1000);
            if (StringUtils.isNotBlank(OK) && OK.equals(OK)) {
                //10秒执行数据库，超时回会出现问题
                pmsSkuInfo = getSkuByIdFromDb(skuId);
                if (pmsSkuInfo != null) {
                    //数据缓存到redis，并设置随机不同失效时间，防止redis雪崩
                    int cacheTime = new Random().nextInt(10);
                    jedis.setex("sku:" + skuId + ":info", cacheTime + 20, JSON.toJSONString(pmsSkuInfo));
                } else {
                    //数据库中不存在该sku，null或者空字符串值设置给redis，并设置失效时间，防止缓存穿透
                    jedis.setex("sku:" + skuId + ":info", 60 * 5, JSON.toJSONString(""));
                    //分布式锁执行完毕后，释放锁
                    String lockToken = jedis.get("sku:" + skuId + ":lock");
                    if (StringUtils.isNotBlank(lockToken) && lockToken.equals(token)) {
                        jedis.del("sku:" + skuId + ":lock");// 用token确认删除的是自己的sku的锁
                    }
                }
            }
        }
        return pmsSkuInfo;
    }

    public PmsSkuInfo getSkuByIdFromDb(String skuId) {
        // sku商品对象
        PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOneBySkuId(skuId);
        // sku的图片集合
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.select(pmsSkuImage);
        skuInfo.setSkuImageList(pmsSkuImages);
        return skuInfo;
    }

}
