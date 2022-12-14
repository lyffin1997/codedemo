package com.lyffin.shiro.config;

import com.lyffin.mybatis.mapper.UserMapper;
import com.lyffin.mybatis.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

//自定义Realm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //拿到当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        //从对象中拿到登录的user信息，认证时传递的user信息
        User currentUser = (User)subject.getPrincipal();
        //设置当前用户权限
        info.addStringPermissions(Arrays.asList(currentUser.getPerms().split(",")));

        //return null; 返回null的话授权不会生效
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");
        //从数据库取账号密码
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        User user = userMapper.queryUserByName(userToken.getUsername());
        //账号认证
        if (user == null) {
            return null; //会抛出异常UnknownAccountException，被controller里subject.login方法捕获
        }
        //密码认证，shiro已经加密
        //也可以自己设置加密方式，默认MD5加密
        //第一个参数将user信息传递到subject中，方便授权时获取user信息
        //第二个参数为用户的密码
        return new SimpleAuthenticationInfo(user, user.getPwd(), "");
    }
}
