package com.lyffin.demo.oauth2.service;

import com.lyffin.demo.oauth2.pojo.User;

public interface UserService {
    public User findUserByUserName(String username);
}
