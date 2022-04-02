package com.my.gmall.list.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.my.gmall.ListService;
import com.my.gmall.ManageService;
import com.my.gmall.bean.BaseAttrInfo;
import com.my.gmall.bean.BaseAttrValue;
import com.my.gmall.bean.SkuLsParams;
import com.my.gmall.bean.SkuLsResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-29 7:26
 */
@Controller
public class ListController {
    @Reference
    private ListService listService;
    @Reference
    private ManageService manageService;

    @GetMapping("list.html")
    public String getList(SkuLsParams skuLsParams, Model model){
        SkuLsResult search = listService.search(skuLsParams);

        //已选面包屑的内容
        List baseAttrValuesList = new ArrayList();

        //从结果中获取平台属性值Id列表
        List<String> attrValueIdList = search.getAttrValueIdList();
        //通过平台属性值，获取平台属性id 和 平台属性值
        List<BaseAttrInfo> attrList = manageService.getAttrList(attrValueIdList);

        //skuLsParams 中的valueId 和 attrList中的valueId相同，则从attrList删除该valueId
        for (Iterator<BaseAttrInfo> iterator = attrList.iterator(); iterator.hasNext(); ) {
            BaseAttrInfo attrInfo =  iterator.next();
            for (BaseAttrValue attrValue: attrInfo.getAttrValueList()) {
                if(skuLsParams.getValueId() != null && skuLsParams.getValueId().length > 0){
                    for(String valueId : skuLsParams.getValueId()){
                        //选中的属性值 和 查询结果的属性值相同 则移除
                        if(valueId.equals(attrValue.getId())){
                            iterator.remove();

                            //组成面包屑列表
                            BaseAttrValue baseAttrValueSelected  = new BaseAttrValue();
                            baseAttrValueSelected.setValueName(attrInfo.getAttrName()+":"+
                                    attrValue.getValueName());

                            //去除重复数据
                            String makeUrlParam = makeUrlParam(skuLsParams,valueId);

                            baseAttrValuesList.add(baseAttrValueSelected);

                        }


                    }
                }
            }
        }

        //已选的属性值列表
        String urlParam = makeUrlParam(skuLsParams);

        model.addAttribute("skuLsInfoList",search.getSkuLsInfoList());
        model.addAttribute("attrList",attrList);
        model.addAttribute("urlParam",urlParam);
        //面包屑 要展示的内容
        model.addAttribute("baseAttrValuesList",baseAttrValuesList);

        return "list";

    }
    //拼接条件
    private String makeUrlParam(SkuLsParams skuLsParams,String... excludeValueIds) {
        String urlParam = "";
        if(skuLsParams.getKeyword() != null){
            urlParam += "keyword="+skuLsParams.getKeyword();
        }

        if(skuLsParams.getCatalog3Id() != null && skuLsParams.getCatalog3Id().length() > 0){
            if(urlParam.length() > 0){
                urlParam += "&";
            }
            urlParam += "catalog3Id="+skuLsParams.getCatalog3Id();
        }

        if(skuLsParams.getValueId() != null && skuLsParams.getValueId().length > 0){
            for (int i=0;i<skuLsParams.getValueId().length;i++){
                String valueId = skuLsParams.getValueId()[i];
                if (excludeValueIds!=null && excludeValueIds.length>0){
                    String excludeValueId = excludeValueIds[0];
                    if (excludeValueId.equals(valueId)){
                        // 跳出代码，后面的参数则不会继续追加【后续代码不会执行】
// 不能写break；如果写了break；其他条件则无法拼接！
                        continue;
                    }
                }
                if(urlParam.length() > 0){
                    urlParam += "&";
                }
                urlParam += valueId;
            }
        }


        return urlParam;
    }
}
