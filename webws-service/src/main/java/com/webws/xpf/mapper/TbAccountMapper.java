package com.webws.xpf.mapper;

import com.webws.xpf.model.TbAccount;

public interface TbAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbAccount record);

    int insertSelective(TbAccount record);

    TbAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbAccount record);

    int updateByPrimaryKey(TbAccount record);
}