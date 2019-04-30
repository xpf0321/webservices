package com.webws.xpf.service;

import com.webws.xpf.model.Userinfo;

public interface UserService extends IService<Userinfo> {
    Userinfo findByUserName(String username);
}
