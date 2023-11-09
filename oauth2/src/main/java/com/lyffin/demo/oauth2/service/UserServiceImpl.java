package com.lyffin.demo.oauth2.service;


import com.lyffin.demo.oauth2.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User findUserByUserName(String username) {
        User user = new User();
        user.setId(1);
        user.setUsername("D46033");
        user.setPassword("$2a$10$Pgs46f8LzTjOvA5Sg6qDkOBbUoAtWQQdHFoEbbmWPak.34/NwJQrW");
        return user;
    }
}
