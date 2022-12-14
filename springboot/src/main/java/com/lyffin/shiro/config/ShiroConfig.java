package com.lyffin.shiro.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro三大属性
 * subject：当前对象
 * securityManager：管理所有对象
 * reaml：访问数据（相当于持久层）
 */
@Configuration
public class ShiroConfig {

    //第三步：shiro filter factory bean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置securityManager
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro内置过滤器
        /**
         * anon:无需认证
         * authc:必须认证才能访问
         * user:必须拥有 记住我 功能才能用
         * perms:拥有对某个资源的权限才能访问
         * role:拥有某个角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        //设置页面访问权限
        //filterMap.put("/user/add", "authc");
        //filterMap.put("/user/update", "authc");
        //意思是必须是用户有user:add权限才能访问/user/add
        filterMap.put("/user/add", "perms[user:add]");//注意这行代码得在authc前面，不然会拦截不了
        filterMap.put("/user/update", "perms[user:update]");
        filterMap.put("/user/*", "authc");
        bean.setFilterChainDefinitionMap(filterMap);
        //设置无权限时跳转登录页
        bean.setLoginUrl("/toLogin");
        //设置未授权时的请求
        bean.setUnauthorizedUrl("/noauth");

        return bean;

    }
    //第二步：default web security manager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //第一步：创建 realm对象，需要自定义类
    @Bean(name="userRealm")//默认用方法名做名字，因此这里也可以不加name属性
    public UserRealm userRealm() {
        return new UserRealm();
    }

}
