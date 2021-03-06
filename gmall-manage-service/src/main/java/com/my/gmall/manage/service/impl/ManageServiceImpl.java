package com.my.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.gmall.ManageService;
import com.my.gmall.bean.*;
import com.my.gmall.config.RedisUtil;
import com.my.gmall.manage.constant.ManageConst;
import com.my.gmall.manage.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

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

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private RedisUtil redisUtil;

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
        //????????????????????????
        if(baseAttrInfo.getId() != null && baseAttrInfo.getId().length() > 0) {
            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
        }else{
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }

        //???????????????????????????????????????????????????
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValue);

        //?????????????????????
        if(baseAttrInfo.getAttrValueList() != null && baseAttrInfo.getAttrValueList().size() > 0){
            for (BaseAttrValue attrValue  : baseAttrInfo.getAttrValueList()) {
//                ?????????????????????
                attrValue.setId(null);
                attrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insertSelective(attrValue);
            }
        }


    }

    @Override
    public BaseAttrInfo getAttrInfo(String attrId) {
        //??????attrId ??????????????????
        BaseAttrInfo attrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);

        //???????????????????????????
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
        //?????? ??? ?????? ?????????????????? ???????????????????????????ID
        if(spuInfo.getId() == null || spuInfo.getId().equals("")){
            //????????????
            spuInfoMapper.insertSelective(spuInfo);
        }else{
            spuInfoMapper.updateByPrimaryKeySelective(spuInfo);
        }
        //??????spu??????
            //?????? spu?????????????????????,?????????
        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuInfo.getId());
        spuImageMapper.delete(spuImage);
            //??????spuImg
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        for (SpuImage image : spuImageList) {
            image.setId(null);
            image.setSpuId(spuInfo.getId());
            spuImageMapper.insertSelective(image);
        }

        //??????spu???????????????
            //1?????????????????????????????????

        SpuSaleAttr spuSaleAttr = new SpuSaleAttr();
        spuSaleAttr.setSpuId(spuInfo.getId());
        spuSaleAttrMapper.delete(spuSaleAttr);
        
            //2??????????????? ?????????????????????
        SpuSaleAttrValue spuSaleAttrValue = new SpuSaleAttrValue();
        spuSaleAttrValue.setSpuId(spuInfo.getId());
        spuSaleAttrValueMapper.delete(spuSaleAttrValue);
        
            //3???????????????????????? ???????????????
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        for (SpuSaleAttr saleAttr : spuSaleAttrList) {
            saleAttr.setId(null);
            saleAttr.setSpuId(spuInfo.getId());
            spuSaleAttrMapper.insertSelective(saleAttr);

            //???????????????
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

        //1??? skuInfo
        if(skuInfo.getId() == null || skuInfo.getId().length()==0){
            skuInfoMapper.insertSelective(skuInfo);
        }else{
            skuInfoMapper.updateByPrimaryKeySelective(skuInfo);
        }

        //2???skuImage
        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuInfo.getId());
        skuImageMapper.delete(skuImage);

        //3???sku_attr_value

        //4???skuSaleAttrValue
    }

    /**
     * ??????devtool??????
     * @param skuId
     * @return
     */
//    public SkuInfo getSkuInfo(String skuId){
//        SkuInfo skuInfo = getSkuInfoDB(skuId);
//        return skuInfo;
//    }


    /**
     * ??????????????????skuInfo
     * @param skuId
     * @return
     */
    @Override
    public SkuInfo getSkuInfo(String skuId) {

        //??????redis
        Jedis jedis = redisUtil.getJedis();
        SkuInfo skuInfo = null;
        String skuKey = ManageConst.SKUKEY_PREFIX+skuId+ManageConst.SKUKEY_SUFFIX;
        String skuJson = jedis.get(skuKey);
        if(skuJson == null || skuJson.length()==0){
            //???????????????key???null,????????????????????????????????? ?????? ????????????
            //?????????
            String skuLockKey = ManageConst.SKUKEY_PREFIX+skuId+ManageConst.SKULOCK_SUFFIX;
            String lockKey = jedis.set(skuLockKey,"good","NX","PX",ManageConst.SKULOCK_EXPIRE_PX);
            if("OK".equals(lockKey)){
                //?????????????????????????????????
                skuInfo = getSkuInfoDB(skuId);
                String skuString = JSON.toJSONString(skuInfo);
                //????????????redis
                jedis.setex(skuKey,ManageConst.SKULOCK_EXPIRE_PX,skuString);
            }else{
                //??????????????? ????????????
                //?????????
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //????????????
                return getSkuInfo(skuId);
            }

        }else{
            //????????????????????????????????????????????????????????????
             skuInfo = JSON.parseObject(skuJson, SkuInfo.class);
        }

        jedis.close();
        return skuInfo;
    }

    /**
     * ???????????????????????????skuInfo
     * @param skuId
     * @return
     */
    public SkuInfo getSkuInfoDB(String skuId) {
        //skuInfo????????????
        SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(skuId);
        //skuImage
        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuId);
        List<SkuImage> skuImages = skuImageMapper.select(skuImage);
        skuInfo.setSkuImageList(skuImages);

        //????????????
        SkuAttrValue skuAttrValue = new SkuAttrValue();
        skuAttrValue.setSkuId(skuId);
        List<SkuAttrValue> skuAttrList = skuAttrValueMapper.select(skuAttrValue);
        skuInfo.setSkuAttrValueList(skuAttrList);
        return skuInfo;
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(SkuInfo skuInfo) {

        List<SpuSaleAttr> spuSaleAttrList = spuSaleAttrMapper.selectSpuSaleAttrListCheckBySku(skuInfo.getId(), skuInfo.getSpuId());
        return spuSaleAttrList;
    }

    @Override
    public List<SkuSaleAttrValue> getSkuSaleAttrValueListBySpu(String spuId) {
        return skuSaleAttrValueMapper.getSkuSaleAttrValueListBySpu(spuId);
    }

    @Override
    public List<BaseAttrInfo> getAttrList(List<String> attrValueIdList) {

        String attrValueIds = StringUtils.join(attrValueIdList,",");
        List<BaseAttrInfo> baseAttrInfoList =  baseAttrInfoMapper.selectAttrInfoListByIds(attrValueIds);
        return baseAttrInfoList;
    }
}
