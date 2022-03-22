package com.my.gmall.manageweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.gmall.ManageService;
import com.my.gmall.bean.SkuInfo;
import com.my.gmall.bean.SpuImage;
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
    
    @GetMapping("/spuImageList")
    public List<SpuImage> getSpuImageList(String spuId){
        List<SpuImage> spuImageList = manageService.getSpuImageList(spuId);
        return spuImageList;
    }

    @GetMapping("/saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody SkuInfo skuInfo){
        manageService.saveSkuInfo(skuInfo);
        return "ok";
    }
}
