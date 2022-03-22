package com.my.gmall.manage.mapper;

import com.my.gmall.bean.SpuSaleAttr;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-21 14:47
 */
public interface SpuSaleAttrMapper extends Mapper<SpuSaleAttr> {

    List<SpuSaleAttr> selectSpuSaleAttrList(String spuId);
}
