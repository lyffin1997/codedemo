package com.lyffin.security.distributed.uaa.service;



import com.lyffin.security.distributed.uaa.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User findUserByUserName(String username) {
        User user = new User();
        user.setUsername("D46033");
        //1234
        user.setPassword("$2a$10$Pgs46f8LzTjOvA5Sg6qDkOFR..jRQUNy9ptOyXO2OpNcjrATfGJWG");
        user.setPerms("res1");
        return user;
    }
}
