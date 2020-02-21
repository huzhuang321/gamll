package com.gmall.manage.controller;

import com.gmall.bean.PmsProductImage;
import com.gmall.bean.PmsProductInfo;
import com.gmall.bean.PmsProductSaleAttr;
import com.gmall.bean.PmsProductSaleAttrValue;
import com.gmall.manage.service.SpuService;
import com.gmall.util.Constants;
import com.gmall.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品spu存储单元控制器
 */
@RestController
public class SpuController {
    @Autowired
    private SpuService spuService;

    /**
     * 查询所有商品spu属性
     *
     * @param catalog3Id
     * @return
     */
    @RequestMapping("spu_list")
    public ResponseMessage spuList(String catalog3Id) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<PmsProductInfo> pmsProductInfos = spuService.spuList(catalog3Id);
        if (CollectionUtils.isEmpty(pmsProductInfos)) {
            responseMessage.setStatus(Constants.Http._404);
            responseMessage.setError(Constants.Response.ERROR);
            responseMessage.setMessage("查询数据为空");
            return responseMessage;
        }
        responseMessage.setData(pmsProductInfos);
        responseMessage.setStatus(Constants.Http._200);
        responseMessage.setError(Constants.Response.SUCCESS);
        responseMessage.setMessage("查询数据成功");
        return responseMessage;
    }

    /**
     * 根据商品productId获取商品销售属性
     *
     * @param productId
     * @return
     */
    @RequestMapping("spu_sale_attr")
    public ResponseMessage spuSaleAttr(@RequestParam String productId) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttr(productId);
        if (CollectionUtils.isEmpty(pmsProductSaleAttrs)) {
            responseMessage.setStatus(Constants.Http._404);
            responseMessage.setMessage("查询数据为空");
            return responseMessage;
        }
        responseMessage.setData(pmsProductSaleAttrs);
        responseMessage.setStatus(Constants.Http._200);
        responseMessage.setError(Constants.Response.SUCCESS);
        responseMessage.setMessage("查询数据成功");
        return responseMessage;
    }

    /**
     * 添加spu时查询销售属性值
     *
     * @param pmsProductSaleAttr
     * @return
     */
    @RequestMapping("spuSaleAttrValue")
    public ResponseMessage spuSaleAttrValue(@RequestBody PmsProductSaleAttr pmsProductSaleAttr) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = spuService.spuSaleAttrValue(pmsProductSaleAttr);
        if (CollectionUtils.isEmpty(pmsProductSaleAttrValues)) {
            responseMessage.setStatus(Constants.Http._404);
            responseMessage.setMessage("查询数据为空");
            return responseMessage;
        }
        responseMessage.setData(pmsProductSaleAttrValues);
        responseMessage.setStatus(Constants.Http._200);
        responseMessage.setError(Constants.Response.SUCCESS);
        responseMessage.setMessage("查询数据成功");
        return responseMessage;
    }

    /**
     * 添加spu商品信息
     *
     * @param pmsProductInfo
     * @return
     * @RequestBody 请求参数为json
     */
    @RequestMapping("saveSpuInfo")
    public ResponseMessage saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            spuService.savePmsProductInfo(pmsProductInfo);
            responseMessage.setStatus(Constants.Http._200);
            responseMessage.setError(Constants.Response.SUCCESS);
            responseMessage.setMessage("添加数据成功");

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setStatus(Constants.Http._500);
            responseMessage.setError(Constants.Response.ERROR);
            responseMessage.setMessage("添加数据失败");
        }

        return responseMessage;
    }

    /**
     * 删除spu商品信息
     *
     * @param spuId
     * @return
     */
    @RequestMapping("delSpuInfo")
    public ResponseMessage delSpuInfo(@RequestParam String spuId) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            spuService.delSpuInfo(spuId);
            responseMessage.setStatus(Constants.Http._200);
            responseMessage.setError(Constants.Response.SUCCESS);
            responseMessage.setMessage("删除商品spu成功");

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setStatus(Constants.Http._500);
            responseMessage.setError(Constants.Response.ERROR);
            responseMessage.setMessage("删除商品SPU失败");
        }

        return responseMessage;
    }

    @RequestMapping("spu_image_list")
    public ResponseMessage spuImageList(@RequestParam String productId) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<PmsProductImage> productImages = spuService.spuImageList(productId);
        if (CollectionUtils.isEmpty(productImages)) {
            responseMessage.setStatus(Constants.Http._404);
            responseMessage.setMessage("查询数据为空");
            return responseMessage;
        }
        responseMessage.setData(productImages);
        responseMessage.setStatus(Constants.Http._200);
        responseMessage.setError(Constants.Response.SUCCESS);
        responseMessage.setMessage("查询数据成功");
        return responseMessage;
    }
}
