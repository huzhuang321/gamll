package com.gmall.manage.controller;

import com.gmall.bean.PmsBaseCatalog1;
import com.gmall.bean.PmsBaseCatalog2;
import com.gmall.bean.PmsBaseCatalog3;
import com.gmall.manage.service.CatalogService;
import com.gmall.util.Constants;
import com.gmall.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类控制层
 */
@RestController
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @RequestMapping("/getCatalog1")
    public ResponseMessage getCatalog1() {
        ResponseMessage responseMessage = new ResponseMessage();
        List<PmsBaseCatalog1> pmsBaseCatalog1s = catalogService.getCatalog1();
        if (CollectionUtils.isEmpty(pmsBaseCatalog1s)) {
            responseMessage.setError(Constants.Response.NOT_FOUNT);
            responseMessage.setStatus(Constants.Http._500);
            return responseMessage;
        }
        responseMessage.setMessage(Constants.Response.SUCCESS);
        responseMessage.setStatus(Constants.Http._200);
        responseMessage.setData(pmsBaseCatalog1s);
        return responseMessage;
    }

    @GetMapping("getCatalog2")
    public ResponseMessage getCatalog2(@RequestParam String catalog1Id) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<PmsBaseCatalog2> pmsBaseCatalog2s = catalogService.getCatalog2(catalog1Id);
        if (CollectionUtils.isEmpty(pmsBaseCatalog2s)) {
            responseMessage.setError(Constants.Response.NOT_FOUNT);
            responseMessage.setStatus(Constants.Http._500);
            return responseMessage;
        }
        responseMessage.setMessage(Constants.Response.SUCCESS);
        responseMessage.setStatus(Constants.Http._200);
        responseMessage.setData(pmsBaseCatalog2s);
        return responseMessage;
    }


    @GetMapping("getCatalog3")
    public ResponseMessage getCatalog3(@RequestParam String catalog2Id) {

        ResponseMessage responseMessage = new ResponseMessage();
        List<PmsBaseCatalog3> pmsBaseCatalog3s = catalogService.getCatalog3(catalog2Id);
        if (CollectionUtils.isEmpty(pmsBaseCatalog3s)) {
            responseMessage.setError(Constants.Response.NOT_FOUNT);
            responseMessage.setStatus(Constants.Http._500);
            return responseMessage;
        }
        responseMessage.setMessage(Constants.Response.SUCCESS);
        responseMessage.setStatus(Constants.Http._200);
        responseMessage.setData(pmsBaseCatalog3s);
        return responseMessage;
    }


}
