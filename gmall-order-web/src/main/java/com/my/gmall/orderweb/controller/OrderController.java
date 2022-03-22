package com.my.gmall.orderweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.gmall.UserInfoService;
import com.my.gmall.bean.UserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-17 16:30
 */
@RestController
public class OrderController {

    @Reference
    private UserInfoService userInfoService;

    @GetMapping("/trade")
    public List<UserAddress> trade(String userId){
        return userInfoService.getUserAddressList(userId);
    }
}
