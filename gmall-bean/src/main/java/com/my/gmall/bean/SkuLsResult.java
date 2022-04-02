package com.my.gmall.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-29 6:28
 */
@Data
public class SkuLsResult implements Serializable {

     List<SkuLsInfo> skuLsInfoList;
     long total;
     long totalPages;
     List<String> attrValueIdList;
}
