package com.my.gmall.usermanage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.my.gmall.UserInfoService;
import com.my.gmall.bean.UserAddress;
import com.my.gmall.bean.UserInfo;
import com.my.gmall.usermanage.mapper.UserAddressMapper;
import com.my.gmall.usermanage.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-17 16:03
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;
    @Override
    public List<UserInfo> getUserInfoList() {
        return userInfoMapper.selectAll();
    }

    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }
}
