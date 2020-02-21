package com.gmall.item.service;

import com.gmall.util.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "manage-service")
public interface ItemClient {
    @RequestMapping("/searchPmsSkuInfoMsg")
    ResponseMessage searchPmsSkuInfo(String skuId);

    @GetMapping("getCatalog2")
    ResponseMessage getCatalog2(@RequestParam String catalog1Id);
}
