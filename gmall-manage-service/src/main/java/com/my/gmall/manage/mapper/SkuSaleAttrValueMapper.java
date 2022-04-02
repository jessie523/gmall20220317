package com.my.gmall.manage.mapper;

import com.my.gmall.bean.SkuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-22 11:48
 */
public interface SkuSaleAttrValueMapper extends Mapper<SkuSaleAttrValue> {
    //获取一个spu下，能够组成的所有sku
    public List<SkuSaleAttrValue> getSkuSaleAttrValueListBySpu(String skuId);
}
