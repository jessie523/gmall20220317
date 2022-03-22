package com.my.gmall;

import com.my.gmall.bean.UserAddress;
import com.my.gmall.bean.UserInfo;

import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-17 16:01
 */
public interface UserInfoService {

    List<UserInfo> getUserInfoList();

    public List<UserAddress> getUserAddressList(String userId);
}
