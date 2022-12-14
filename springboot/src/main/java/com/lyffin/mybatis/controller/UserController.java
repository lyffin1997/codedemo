package com.lyffin.mybatis.controller;



import com.lyffin.mybatis.mapper.UserMapper;
import com.lyffin.mybatis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/queryUserList")
    public List<User> queryUserList() {
        List<User> list = userMapper.queryUserList();
        return list;
    }
}


