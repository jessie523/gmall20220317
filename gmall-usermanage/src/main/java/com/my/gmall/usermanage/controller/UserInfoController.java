package com.my.gmall.usermanage.controller;

import com.my.gmall.UserInfoService;
import com.my.gmall.bean.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * author:zxy
 *
 * @create 2022-03-17 16:06
 */
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ResponseBody
    @GetMapping("/findAll")
    public List<UserInfo> findAll(){

        return userInfoService.getUserInfoList();
    }
}
