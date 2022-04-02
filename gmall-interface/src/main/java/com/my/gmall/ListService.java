package com.my.gmall;

import com.my.gmall.bean.SkuLsAttrValue;
import com.my.gmall.bean.SkuLsInfo;
import com.my.gmall.bean.SkuLsParams;
import com.my.gmall.bean.SkuLsResult;

/**
 * author:zxy
 *
 * @create 2022-03-28 11:47
 */
public interface ListService {

    //从mysql 到es （上架）
    public void saveSkuLsInfo(SkuLsInfo skuLsInfo);

    public SkuLsResult search(SkuLsParams skuLsParams);

    //更新热度评分
    public void incrHotScore(String skuId);
}
