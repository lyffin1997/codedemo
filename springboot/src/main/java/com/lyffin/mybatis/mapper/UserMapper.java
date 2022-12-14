package com.lyffin.mybatis.mapper;


import com.lyffin.mybatis.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    public List<User> queryUserList();
    public User queryUserByName(String name);

}
