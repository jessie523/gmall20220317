package com.my.gmall.manage.mapper;

import com.my.gmall.bean.BaseAttrInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-17 22:10
 */
public interface BaseAttrInfoMapper extends Mapper<BaseAttrInfo> {

    public List<BaseAttrInfo> getBaseAttrInfoListByCatalog3Id(String catalog3Id);

    List<BaseAttrInfo> selectAttrInfoListByIds(@Param("valueIds")String attrValueIds);
}
