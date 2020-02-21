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

    /**
     * 根据skuid查询商品详情信息
     * @param skuId
     * @return
     */
    @RequestMapping("/serchItemMsg")
    public ResponseMessage serchItemMsg(String skuId) {
        ResponseMessage responseMessage = itemClient.searchPmsSkuInfo(skuId);
        return responseMessage;
    }

    /**
     * 根据一级分类商品catalog1Id查询二级分类
     * @param catalog1Id
     * @return
     */
    @RequestMapping("/getCatalog2")
    public ResponseMessage getCatalog2(@RequestParam String catalog1Id) {
        ResponseMessage responseMessage = itemClient.getCatalog2(catalog1Id);
        return responseMessage;
    }
}
