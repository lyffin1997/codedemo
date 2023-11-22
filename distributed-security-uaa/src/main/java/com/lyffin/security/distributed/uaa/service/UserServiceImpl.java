package com.lyffin.security.distributed.uaa.service;



import com.lyffin.security.distributed.uaa.model.MyUserDetail;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public MyUserDetail findUserByUserName(String username) {
        MyUserDetail user = new MyUserDetail();
        user.setUsername("D46033");
        //1234
        user.setPassword("$2a$10$Pgs46f8LzTjOvA5Sg6qDkOFR..jRQUNy9ptOyXO2OpNcjrATfGJWG");
        user.setPerms("p1");
        return user;
    }
}
