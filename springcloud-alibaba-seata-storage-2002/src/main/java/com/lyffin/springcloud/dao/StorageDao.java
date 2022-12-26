package com.lyffin.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StorageDao {
    void decrease(@Param("productId") Long id, @Param("count") Integer count);
}
