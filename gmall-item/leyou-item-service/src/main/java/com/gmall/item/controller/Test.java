package com.gmall.item.controller;

import com.gmall.item.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @Autowired
    private TestService testService;

    @RequestMapping("t")
    public Object test() {
        Object catalog1 = testService.getCatalog1();
        return catalog1;
    }
}
