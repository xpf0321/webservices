package com.webws.xpf.service.impl;

import com.webws.xpf.mapper.UserinfoMapper;
import com.webws.xpf.model.Userinfo;
import com.webws.xpf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseService<Userinfo> implements UserService {

    @Autowired
    UserinfoMapper userInfoMapper;

    @Override
    @Cacheable(value = "my-redis-cache1")
    public Userinfo findByUserName(String username) {
        System.out.println("---------------进入数据库----------------");
        return userInfoMapper.findByName(username);
    }
}
