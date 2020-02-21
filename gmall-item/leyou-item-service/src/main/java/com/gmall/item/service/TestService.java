package com.gmall.item.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "manage-service")
public interface TestService {
    @RequestMapping(value = "/getCatalog1", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    Object getCatalog1();
}
