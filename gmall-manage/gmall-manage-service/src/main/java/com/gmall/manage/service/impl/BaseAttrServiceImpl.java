package com.gmall.manage.service.impl;

import com.gmall.bean.PmsBaseAttrInfo;
import com.gmall.bean.PmsBaseAttrValue;
import com.gmall.manage.mapper.PmsBaseAttrInfoMaper;
import com.gmall.manage.mapper.PmsBaseAttrValueMapper;
import com.gmall.manage.service.BaseAttrService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseAttrServiceImpl implements BaseAttrService {
    @Autowired
    private PmsBaseAttrInfoMaper pmsBaseAttrInfoMaper;
    @Autowired
    private PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    /**
     * 根据商品三级分类catalog3Id查询平台属性和属性值列表
     *
     * @param catalog3Id
     * @return
     */
    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMaper.select(pmsBaseAttrInfo);

        for (PmsBaseAttrInfo baseAttrInfo : pmsBaseAttrInfos) {
            List<PmsBaseAttrValue> pmsBaseAttrValues = new ArrayList<>();
            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(baseAttrInfo.getId());
            pmsBaseAttrValues = pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
            baseAttrInfo.setAttrValueList(pmsBaseAttrValues);
        }
        return pmsBaseAttrInfos;
    }

    /**
     * 新增商品spu时需要查询平台属性,进行新增spu
     *
     * @return
     */
    @Override
    public List<PmsBaseAttrInfo> baseSaleAttrList() {
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMaper.selectAll();
        return pmsBaseAttrInfos;
    }

    /**
     * 新增平台销售属性和属性值
     *
     * @param pmsBaseAttrInfo
     * @return
     */
    @Transactional
    @Override
    public boolean saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        boolean insert = false;
        try {
            String id = pmsBaseAttrInfo.getId();
            if (StringUtils.isBlank(id)) {
                // id为空，保存
                // 保存属性
                pmsBaseAttrInfoMaper.insertSelective(pmsBaseAttrInfo);//insert insertSelective 是否将null插入数据库
                // 保存属性值
                List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                    pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                    pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
                }
                insert = true;
            } else {
                // id不空，修改
                // 属性修改
                Example example = new Example(PmsBaseAttrInfo.class);
                example.createCriteria().andEqualTo("id", pmsBaseAttrInfo.getId());
                pmsBaseAttrInfoMaper.updateByExampleSelective(pmsBaseAttrInfo, example);
                // 属性值修改
                // 按照属性id删除所有属性值
                PmsBaseAttrValue pmsBaseAttrValueDel = new PmsBaseAttrValue();
                pmsBaseAttrValueDel.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrValueMapper.delete(pmsBaseAttrValueDel);
                // 删除后，将新的属性值插入
                List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                    pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                    pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
                }
                insert = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            insert = false;
        }
        return insert;
    }

}
