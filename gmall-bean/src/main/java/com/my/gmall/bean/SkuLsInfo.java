package com.my.gmall.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-27 20:12
 */
@Data
public class SkuLsInfo implements Serializable {
    //不加注解，是因为 不是数据库中的字段，业务需要
    String id;

    BigDecimal price;

    String skuName;

    String catalog3Id;

    String skuDefaultImg;

    Long hotScore=0L;

    List<SkuLsAttrValue> skuAttrValueList;
}
