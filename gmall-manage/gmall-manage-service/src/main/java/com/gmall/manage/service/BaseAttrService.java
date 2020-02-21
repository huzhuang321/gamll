package com.gmall.manage.service;

import com.gmall.bean.PmsBaseAttrInfo;

import java.util.List;

public interface BaseAttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    List<PmsBaseAttrInfo> baseSaleAttrList();

    boolean saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);
}
