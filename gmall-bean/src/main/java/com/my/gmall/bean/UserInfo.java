package com.my.gmall.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * author:zxy
 *
 * @create 2022-03-17 15:56
 */
@Data
public class UserInfo {
    @Id
    @Column
    private String id;
    @Column
    private String loginName;
    @Column
    private String nickName;
    @Column
    private String passwd;
    @Column
    private String name;
    @Column
    private String phoneNum;
    @Column
    private String email;
    @Column
    private String headImg;
    @Column
    private String userLevel;
}
