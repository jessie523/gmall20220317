package com.my.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.my.gmall.ListService;
import com.my.gmall.ManageService;
import com.my.gmall.bean.SkuInfo;
import com.my.gmall.bean.SkuSaleAttrValue;
import com.my.gmall.bean.SpuSaleAttr;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:zxy
 *
 * @create 2022-03-22 15:50
 */
@Controller
public class ItemController {

    @Reference
    private ManageService manageService;

    @Reference
    private ListService listService;

    @GetMapping("{skuId}.html")
    public String skuInfoPage(@PathVariable("skuId")String skuId,Model model){

        Object obj = manageService.getSkuInfo(skuId);
        SkuInfo skuInfo = (SkuInfo) obj;
        //获取sku的销售属性
        List<SpuSaleAttr> saleAttrList = manageService.getSpuSaleAttrListCheckBySku(skuInfo);

        List<SkuSaleAttrValue> skuSaleAttrValueList = manageService.getSkuSaleAttrValueListBySpu(skuInfo.getSpuId());
        //拼json串{"114|116",33} {"115|117",34}
        Map<String,Object> map =new HashMap<>();
        String key ="";
        for(int i = 0;i < skuSaleAttrValueList.size();i++){
            SkuSaleAttrValue saleAttrValue = skuSaleAttrValueList.get(i);
            String valueKey = saleAttrValue.getSaleAttrValueId();
            /**
             * | 加在后面，不好处理
             * 第一次：key = 114|
             * 第二次：key = 114|116|
             *
             * | 加在前面
             * 第一次：key = |114
             * 第二次：key = |114|115
             */
            if(key.length() > 0){
                key += "|";
            }
           key += valueKey;

            //什么时候停止拼串，本轮skuId和下一轮 不一致时，清空字符串key
            if((i+1)== skuSaleAttrValueList.size() || !saleAttrValue.getSkuId().equals(skuSaleAttrValueList.get(i+1).getSkuId())){
                    map.put(key,saleAttrValue.getSkuId());
                    key = "";
            }


        }

        String valueSkuJson = JSON.toJSONString(map);
        System.out.println("===========:"+valueSkuJson);

        model.addAttribute("skuInfo",skuInfo);
        model.addAttribute("saleAttrList",saleAttrList);
        model.addAttribute("valuesSkuJson",valueSkuJson);
        //点一次 商品，就增加商品的热度 hotScore
        listService.incrHotScore(skuId);
        return "item";
    }




}
