package com.gmall.manage.controller;

import com.gmall.bean.PmsSkuInfo;
import com.gmall.manage.service.SkuService;
import com.gmall.util.Constants;
import com.gmall.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品sku控制层
 */
@Controller
public class SkuController {
    @Autowired
    private SkuService skuService;

    /**
     * 根据三级分类catalog3Id查询sku商品信息
     *
     * @param catalog3Id
     * @return
     */
    @RequestMapping("skuList")
    @ResponseBody
    public ResponseMessage skuList(String catalog3Id) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<PmsSkuInfo> pmsSkuInfos = skuService.skuList(catalog3Id);
        if (CollectionUtils.isEmpty(pmsSkuInfos)) {
            responseMessage.setMessage(Constants.Response.FAILED);
            responseMessage.setStatus(Constants.Http._404);
        } else {
            responseMessage.setMessage(Constants.Response.SUCCESS);
            responseMessage.setStatus(Constants.Http._200);
            responseMessage.setData(pmsSkuInfos);
        }
        return responseMessage;
    }

    /**
     * 新增sku信息
     *
     * @param pmsSkuInfo
     * @return
     */
    @RequestMapping("SavePmsSkuInfo")
    @ResponseBody
    public ResponseMessage SavePmsSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            skuService.SavePmsSkuInfo(pmsSkuInfo);
            responseMessage.setMessage(Constants.Response.SUCCESS);
            responseMessage.setStatus(Constants.Http._200);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(Constants.Response.FAILED);
            responseMessage.setStatus(Constants.Http._500);
        }
        return responseMessage;
    }
}
