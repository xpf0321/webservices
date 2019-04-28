package com.webws.xpf.controller;

import com.webws.xpf.mapper.UserInfoMapper;
import com.webws.xpf.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    UserInfoMapper userInfoMapper;

    @RequestMapping(name = "/index")
    public String index(){
        System.out.println("12314152534");


        UserInfo users= userInfoMapper.selectByPrimaryKey(1L);
        return users.toString();
        //System.out.println(userInfoMapper.selectByPrimaryKey(1).getClass());


        // Object userInfo=(Object)userInfoMapper.selectByPrimaryKey(1);
       // UserInfo user1=userInfoMapper.selectByPrimaryKey(1);
        //return "111111111111";
    }


}
