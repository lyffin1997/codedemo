package com.lyffin.security.distributed.uaa.service;


import com.lyffin.security.distributed.uaa.model.User;

public interface UserService {
    public User findUserByUserName(String username);
}
