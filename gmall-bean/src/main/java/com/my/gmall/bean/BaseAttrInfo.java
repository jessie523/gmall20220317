package com.my.gmall.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/** 平台属性
 * author:zxy
 *
 * @create 2022-03-17 22:04
 */
@Data
public class BaseAttrInfo implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private String id;
    @Column
    private String attrName;
    @Column
    private String catalog3Id;
    @Transient
    private List<BaseAttrValue> attrValueList;
}
