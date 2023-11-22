package com.lyffin.security.distributed.uaa.service;



import com.lyffin.security.distributed.uaa.model.Permission;
import com.lyffin.security.distributed.uaa.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginUserDetailsService implements UserDetailsService {
    @Resource
    private UserServiceImpl userService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(username);
        if (user == null){
            throw new UsernameNotFoundException("not found");
        }
        return user;
    }
}