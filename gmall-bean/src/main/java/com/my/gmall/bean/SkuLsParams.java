package com.my.gmall.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * author:zxy
 *
 * @create 2022-03-29 6:26
 */
@Data
public class SkuLsParams implements Serializable {
    String  keyword;

    String catalog3Id;

    String[] valueId;

    int pageNo=1;

    int pageSize=20;
}
