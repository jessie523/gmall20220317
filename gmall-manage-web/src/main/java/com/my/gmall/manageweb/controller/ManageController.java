package com.my.gmall.manageweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.gmall.ManageService;
import com.my.gmall.bean.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-17 21:53
 */
@Controller
@CrossOrigin
public class ManageController {

    @Reference
    private ManageService manageService;

    @RequestMapping("getCatalog1")
    @ResponseBody
    public List<BaseCatalog1> getCatalog1(){
        return manageService.getCatalog1();
    }

    @RequestMapping("getCatalog2")
    @ResponseBody
    public List<BaseCatalog2> getCatalog2(String catalog1Id){
        return manageService.getCatalog2(catalog1Id);
    }

    @RequestMapping("getCatalog3")
    @ResponseBody
    public List<BaseCatalog3> getCatalog3(String catalog2Id){
        return manageService.getCatalog3(catalog2Id);
    }

    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<BaseAttrInfo> attrInfoList(String catalog3Id){
        List<BaseAttrInfo> list = manageService.getAttrList(catalog3Id);
        return list;
    }


    @RequestMapping("/saveAttrInfo")
    @ResponseBody
    public void saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        manageService.saveAttrInfo(baseAttrInfo);
    }

    @PostMapping("getAttrValueList")
    @ResponseBody
    public List<BaseAttrValue> getAttrValueList(String attrId){
//        BaseAttrInfo attrInfo = manageService.getAttrInfo(attrId);
        List<BaseAttrValue> attValueList = manageService.getAttValueList(attrId);
//        return attrInfo.getAttrValueList();
        return attValueList;
    }



    @PostMapping("/baseSaleAttrList")
    @ResponseBody
    public List<BaseSaleAttr> getBaseSaleAttrList(){
        List<BaseSaleAttr> baseSaleAttrList = manageService.getBaseSaleAttrList();
        return baseSaleAttrList;
    }


    @PostMapping("/saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody SpuInfo spuInfo){
        manageService.saveSpuInfo(spuInfo);
        return "ok";
    }
}
