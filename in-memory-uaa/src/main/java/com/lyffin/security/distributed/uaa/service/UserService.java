package com.lyffin.security.distributed.uaa.service;


import com.lyffin.security.distributed.uaa.model.MyUserDetail;

public interface UserService {
    public MyUserDetail findUserByUserName(String username);
}
