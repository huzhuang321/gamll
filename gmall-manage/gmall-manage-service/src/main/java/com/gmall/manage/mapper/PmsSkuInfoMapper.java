package com.gmall.manage.mapper;

import com.gmall.bean.PmsSkuInfo;
import tk.mybatis.mapper.common.Mapper;

public interface PmsSkuInfoMapper extends Mapper<PmsSkuInfo> {
    PmsSkuInfo selectOneBySkuId(String skuId);
}
