package com.my.gmall.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * author:zxy
 *
 * @create 2022-03-17 22:03
 */
@Data
public class BaseCatalog2 implements Serializable {
    @Id
    @Column
    private String id;
    @Column
    private String name;
    @Column
    private String catalog1Id;
}
