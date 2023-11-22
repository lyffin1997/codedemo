package com.lyffin.demo.oauth2.service;


import com.lyffin.demo.oauth2.pojo.Permission;
import com.lyffin.demo.oauth2.pojo.User;
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
        //定义权限列表.
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
        if (user.getRole()!=null){
            authorities.add(new SimpleGrantedAuthority(user.getRole().getKeyWord()));
            if (user.getRole().getPermissionList() !=null && user.getRole().getPermissionList().size()>0){
                for (Permission permission : user.getRole().getPermissionList()) {
                    authorities.add(new SimpleGrantedAuthority(permission.getKeyWord()));
                }
            }
        }

        org.springframework.security.core.userdetails.User user1 = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        return user1;
    }
}