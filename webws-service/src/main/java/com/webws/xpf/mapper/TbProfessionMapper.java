package com.webws.xpf.mapper;

import com.webws.xpf.model.TbProfession;

public interface TbProfessionMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(TbProfession record);

    int insertSelective(TbProfession record);

    TbProfession selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(TbProfession record);

    int updateByPrimaryKey(TbProfession record);
}