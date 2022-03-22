package com.my.gmall.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * author:zxy
 *
 * @create 2022-03-21 11:50
 */
@Data
public class BaseSaleAttr implements Serializable {

    @Id
    @Column
    String id ;

    @Column
    String name;
}
