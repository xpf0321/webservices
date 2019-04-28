package com.webws.xpf.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
/**
 Mapper接口：基本的增删查改
 MySqlMapper接口：针对MySql的额外补充接口，支持批量插入
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}

