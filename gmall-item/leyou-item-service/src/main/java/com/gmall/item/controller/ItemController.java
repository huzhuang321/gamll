package com.gmall.item.controller;

import com.gmall.item.service.ItemClient;
import com.gmall.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品详情页控制层
 */
@RestController
public class ItemController {
    @Autowired
    private ItemClient itemClient;

    @RequestMapping("/serchItemMsg")
    public ResponseMessage serchItemMsg(String skuId) {
        ResponseMessage responseMessage = itemClient.searchPmsSkuInfo(skuId);
        return responseMessage;
    }


    @RequestMapping("/getCatalog2")
    public ResponseMessage getCatalog2(@RequestParam String catalog1Id) {
        ResponseMessage responseMessage = itemClient.getCatalog2(catalog1Id);
        return responseMessage;
    }
}
