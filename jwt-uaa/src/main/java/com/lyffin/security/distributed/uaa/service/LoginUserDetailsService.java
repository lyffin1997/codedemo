package com.lyffin.security.distributed.uaa.service;



import com.lyffin.security.distributed.uaa.model.MyUserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginUserDetailsService implements UserDetailsService {
    @Resource
    private UserServiceImpl userService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUserDetail user = userService.findUserByUserName(username);
        if (user == null){
            throw new UsernameNotFoundException("not found");
        }

        return user;
    }
}