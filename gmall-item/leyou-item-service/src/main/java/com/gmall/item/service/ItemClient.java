package com.gmall.item.service;

import com.gmall.util.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * openFeign的使用规则
 * @FeignClient(name = "manage-service") name为被调用服务名
 * @RequestMapping("/searchPmsSkuInfoMsg") 被调用服务的请求路径
 * 返回值必须和被调用服务的返回值相同
 */
@FeignClient(name = "manage-service")
public interface ItemClient {
    @RequestMapping("/searchPmsSkuInfoMsg")
    ResponseMessage searchPmsSkuInfo(String skuId);

    @GetMapping("getCatalog2")
    ResponseMessage getCatalog2(@RequestParam String catalog1Id);
}
