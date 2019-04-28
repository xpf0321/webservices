package com.webws.xpf.mapper;

import com.webws.xpf.model.HibernateSequence;

public interface HibernateSequenceMapper {
    int insert(HibernateSequence record);

    int insertSelective(HibernateSequence record);
}