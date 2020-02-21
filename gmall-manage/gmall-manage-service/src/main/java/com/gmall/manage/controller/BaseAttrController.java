package com.gmall.manage.controller;

import com.gmall.bean.PmsBaseAttrInfo;
import com.gmall.manage.service.BaseAttrService;
import com.gmall.util.Constants;
import com.gmall.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 平台属性控制层
 */
@RestController
public class BaseAttrController {
    @Autowired
    private BaseAttrService baseAttrService;

    /**
     * 根据商品三级分类catalog3Id查询平台属性和属性值列表
     *
     * @param catalog3Id
     * @return
     */
    @RequestMapping("attrInfoList")
    public ResponseMessage attrInfoList(String catalog3Id) {
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = baseAttrService.attrInfoList(catalog3Id);
        ResponseMessage responseMessage = new ResponseMessage();
        if (CollectionUtils.isEmpty(pmsBaseAttrInfos)) {
            responseMessage.setError(Constants.Response.NOT_FOUNT);
            responseMessage.setStatus(Constants.Http._500);
            return responseMessage;
        }
        responseMessage.setMessage(Constants.Response.SUCCESS);
        responseMessage.setStatus(Constants.Http._200);
        responseMessage.setData(pmsBaseAttrInfos);
        return responseMessage;
    }

    /**
     * 新增商品spu时需要查询平台属性,进行新增spu
     *
     * @return
     */
    @RequestMapping("baseSaleAttrList")
    public ResponseMessage baseSaleAttrList() {
        ResponseMessage responseMessage = new ResponseMessage();
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = baseAttrService.baseSaleAttrList();
        if (CollectionUtils.isEmpty(pmsBaseAttrInfos)) {
            responseMessage.setError(Constants.Response.NOT_FOUNT);
            responseMessage.setStatus(Constants.Http._500);
            return responseMessage;
        }
        responseMessage.setMessage(Constants.Response.SUCCESS);
        responseMessage.setStatus(Constants.Http._200);
        responseMessage.setData(pmsBaseAttrInfos);
        return responseMessage;
    }

    /**
     * 新增平台销售属性和属性值
     *
     * @param pmsBaseAttrInfo
     * @return
     */
    @RequestMapping("saveAttrInfo")
    public ResponseMessage saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            boolean insertOrOnt = baseAttrService.saveAttrInfo(pmsBaseAttrInfo);
            if (insertOrOnt == true) {
                responseMessage.setMessage(Constants.Response.SUCCESS);
                responseMessage.setStatus(Constants.Http._200);
                return responseMessage;
            }
            responseMessage.setMessage(Constants.Response.ERROR);
            responseMessage.setStatus(Constants.Http._500);

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(Constants.Response.ERROR);
            responseMessage.setStatus(Constants.Http._500);
        }

        return responseMessage;
    }
}
