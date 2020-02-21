package com.gmall.manage.service;

import com.gmall.bean.PmsSkuInfo;

import java.util.List;

public interface SkuService {
    List<PmsSkuInfo> skuList(String catalog3Id);

    void SavePmsSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuById(String skuId);
}
