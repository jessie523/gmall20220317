package com.my.gmall;

import com.my.gmall.bean.*;

import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-17 22:11
 */
public interface ManageService {
    public List<BaseCatalog1> getCatalog1();

    public List<BaseCatalog2> getCatalog2(String catalog1Id);

    public List<BaseCatalog3> getCatalog3(String catalog2Id);

    public List<BaseAttrInfo> getAttrList(String catalog3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    BaseAttrInfo getAttrInfo(String attrId);

    List<BaseAttrValue> getAttValueList(String attrId);

    List<SpuInfo> getSpuInfoList(SpuInfo spuInfo);

    // 查询基本销售属性表
    List<BaseSaleAttr> getBaseSaleAttrList();
    //保存spu列表
    public void saveSpuInfo(SpuInfo spuInfo);

    //根据spuId获取spuImage
    public List<SpuImage> getSpuImageList(String spuId);

    //获取spu的销售属性
    List<SpuSaleAttr> getSpuSaleAttrList(String spuId);

    void saveSkuInfo(SkuInfo skuInfo);

    SkuInfo getSkuInfo(String skuId);
//    显示spu全部销售属性，同时锁定sku的销售属性
    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(SkuInfo skuInfo);

    //显示spuId下所有 能够组合成的sku
    public List<SkuSaleAttrValue> getSkuSaleAttrValueListBySpu (String spuId);
    //根据 平台属性值id列表，获取平台属性（id 和 value）
    List<BaseAttrInfo> getAttrList(List<String> attrValueIdList);
}
