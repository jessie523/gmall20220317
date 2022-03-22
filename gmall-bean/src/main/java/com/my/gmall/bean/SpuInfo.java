package com.my.gmall.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-18 15:28
 */
@Data
public class SpuInfo implements Serializable {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String spuName;

    @Column
    private String description;

    @Column
    private  String catalog3Id;

    @Transient
    private List<SpuSaleAttr> spuSaleAttrList;
    @Transient
    private List<SpuImage> spuImageList;
}
