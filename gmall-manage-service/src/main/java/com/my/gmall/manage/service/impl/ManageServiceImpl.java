package com.my.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.my.gmall.ManageService;
import com.my.gmall.bean.*;
import com.my.gmall.manage.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-17 22:13
 */
@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;
    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;



    @Override
    public List<BaseCatalog1> getCatalog1() {
        return baseCatalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        return baseCatalog2Mapper.select(baseCatalog2);
    }

    @Override
    public List<BaseCatalog3> getCatalog3(String catalog2Id) {
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);

        return baseCatalog3Mapper.select(baseCatalog3);
    }

    @Override
    public List<BaseAttrInfo> getAttrList(String catalog3Id) {

//        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
//        baseAttrInfo.setCatalog3Id(catalog3Id);
//        return baseAttrInfoMapper.select(baseAttrInfo);

        return baseAttrInfoMapper.getBaseAttrInfoListByCatalog3Id(catalog3Id);
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //更新和添加用一个
        if(baseAttrInfo.getId() != null && baseAttrInfo.getId().length() > 0) {
            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
        }else{
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }

        //把原来的平台属性对应的属性值都删除
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValue);

        //添加平台属性值
        if(baseAttrInfo.getAttrValueList() != null && baseAttrInfo.getAttrValueList().size() > 0){
            for (BaseAttrValue attrValue  : baseAttrInfo.getAttrValueList()) {
//                防止主键被赋值
                attrValue.setId(null);
                attrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insertSelective(attrValue);
            }
        }


    }

    @Override
    public BaseAttrInfo getAttrInfo(String attrId) {
        //根据attrId 查找平台属性
        BaseAttrInfo attrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);

        //查找平台属性值集合
        BaseAttrValue attrValue = new BaseAttrValue();
        attrValue.setAttrId(attrId);
        List<BaseAttrValue> attrValueList = baseAttrValueMapper.select(attrValue);

        attrInfo.setAttrValueList(attrValueList);
        return attrInfo;
    }

    @Override
    public List<BaseAttrValue> getAttValueList(String attrId) {
        BaseAttrValue attrValue = new BaseAttrValue();
        attrValue.setAttrId(attrId);
        return baseAttrValueMapper.select(attrValue);
    }


    @Override
    public List<SpuInfo> getSpuInfoList(SpuInfo spuInfo) {
        return spuInfoMapper.select(spuInfo);
    }

    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }


    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        //保存 和 更新 公用一个方法 区分方法：是否存在ID
        if(spuInfo.getId() == null || spuInfo.getId().equals("")){
            //保存数据
            spuInfoMapper.insertSelective(spuInfo);
        }else{
            spuInfoMapper.updateByPrimaryKeySelective(spuInfo);
        }
        //保存spu图片
            //先把 spu对应的图片删除,再添加
        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuInfo.getId());
        spuImageMapper.delete(spuImage);
            //保存spuImg
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        for (SpuImage image : spuImageList) {
            image.setId(null);
            image.setSpuId(spuInfo.getId());
            spuImageMapper.insertSelective(image);
        }

        //保存spu的销售属性
            //1销售属性先删除，再插入

        SpuSaleAttr spuSaleAttr = new SpuSaleAttr();
        spuSaleAttr.setSpuId(spuInfo.getId());
        spuSaleAttrMapper.delete(spuSaleAttr);
        
            //2销售属性值 先删除，再插入
        SpuSaleAttrValue spuSaleAttrValue = new SpuSaleAttrValue();
        spuSaleAttrValue.setSpuId(spuInfo.getId());
        spuSaleAttrValueMapper.delete(spuSaleAttrValue);
        
            //3添加销售属性，及 销售属性值
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        for (SpuSaleAttr saleAttr : spuSaleAttrList) {
            saleAttr.setId(null);
            saleAttr.setSpuId(spuInfo.getId());
            spuSaleAttrMapper.insertSelective(saleAttr);

            //销售属性值
            List<SpuSaleAttrValue> spuSaleAttrValueList = saleAttr.getSpuSaleAttrValueList();
            for (SpuSaleAttrValue saleAttrValue : spuSaleAttrValueList) {
                saleAttrValue.setId(null);
                saleAttrValue.setSpuId(spuInfo.getId());
                spuSaleAttrValueMapper.insertSelective(saleAttrValue);
            }

        }
    }

    @Override
    public List<SpuImage> getSpuImageList(String spuId) {
        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuId);
        List<SpuImage> spuImageList = spuImageMapper.select(spuImage);
        return spuImageList;
    }


    @Override
    public List<SpuSaleAttr> getSpuSaleAttrList(String spuId) {

        return spuSaleAttrMapper.selectSpuSaleAttrList(spuId);
    }

    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {

        //1、 skuInfo
        if(skuInfo.getId() == null || skuInfo.getId().length()==0){
            skuInfoMapper.insertSelective(skuInfo);
        }else{
            skuInfoMapper.updateByPrimaryKeySelective(skuInfo);
        }

        //2、skuImage
        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuInfo.getId());
        skuImageMapper.delete(skuImage);

        //3、sku_attr_value

        //4、skuSaleAttrValue
    }
}
