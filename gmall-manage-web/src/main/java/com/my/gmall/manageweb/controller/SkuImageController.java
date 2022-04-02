package com.my.gmall.manageweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.gmall.ListService;
import com.my.gmall.ManageService;
import com.my.gmall.bean.SkuInfo;
import com.my.gmall.bean.SkuLsInfo;
import com.my.gmall.bean.SpuImage;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-22 10:00
 */
@CrossOrigin
@RestController
public class SkuImageController {

    @Reference
    private ManageService manageService;
    @Reference
    private ListService listService;
    
    @GetMapping("/spuImageList")
    public List<SpuImage> getSpuImageList(String spuId){
        List<SpuImage> spuImageList = manageService.getSpuImageList(spuId);
        return spuImageList;
    }

    @PostMapping("/saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody SkuInfo skuInfo){
        manageService.saveSkuInfo(skuInfo);
        return "ok";
    }

    @GetMapping("/onSale")
    public void onSale(String skuId){
        //从数据库中查出skuInfo
        SkuInfo skuInfo = manageService.getSkuInfo(skuId);
        SkuLsInfo skuLsInfo = new SkuLsInfo();

        //属性拷贝
        BeanUtils.copyProperties(skuInfo,skuLsInfo);

        listService.saveSkuLsInfo(skuLsInfo);


    }
}
