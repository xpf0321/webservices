package com.webws.xpf.mapper;

import com.webws.xpf.model.Userinfo;
import com.webws.xpf.utils.MyMapper;
import org.apache.ibatis.annotations.Select;

public interface UserinfoMapper extends MyMapper<Userinfo> {
    @Select("select * from  userInfo where name = #{username}")
    Userinfo findByName(String username);

}