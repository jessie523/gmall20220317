package com.my.gmall.manageweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.gmall.ManageService;
import com.my.gmall.bean.SpuInfo;
import com.my.gmall.bean.SpuSaleAttr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-18 15:35
 */
@CrossOrigin
@Controller
public class SpuController {

    @Reference
    private ManageService manageService;


    @GetMapping("/spuList")
    @ResponseBody
    public List<SpuInfo> getSpuList(String catalog3Id){

        SpuInfo spuInfo = new SpuInfo();
        spuInfo.setCatalog3Id(catalog3Id);
        return manageService.getSpuInfoList(spuInfo);
    }

    @GetMapping("/spuSaleAttrList")
    @ResponseBody
    public List<SpuSaleAttr> getspuSaleAttrList(String spuId){
        List<SpuSaleAttr> spuSaleAttrList = manageService.getSpuSaleAttrList(spuId);

        return spuSaleAttrList;

    }

}
