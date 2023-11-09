package com.lyffin.demo.oauth2.config;

import com.lyffin.demo.oauth2.service.LoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    /**
     * 设置认证管理器中的userDetailsService接口实例
     * 结合流程图可知，AuthenticationManager会委托DaoAuthenticationProvider进行认证
     * 而点进源码可知，DaoAuthenticationProvider会调用userDetailsService.loadUserByUsername方法
     * 因此这里要设置自定义的loadUserByUsername方法，否则默认是从内存中找用户信息
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginUserDetailsService)// 设置自定义的userDetailsService
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 放行静态资源文件，springsecurity默认会拦截静态资源文件
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/fonts/**","/images/**","/js/**");
    }

    /**
     * 自定义密码加密器(参考流程图可知密码加密器的作用)
     * 这里使用的是BCrypt加密器
     * 加密器最常用的方法就是加密和比较，一个用来将明文密码加密存入数据库，一个用来认证时将输入的密码和数据库密码对比
     * BCrypt加密方式是不可逆的
     * 这里也可以自定义其他加密方式，如sha256等
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 使用BCrypt加密密码
        return new BCryptPasswordEncoder();
    }


    /**
     * 做一些权限设置，如拦截、跨域、定向跳转等
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();//开启运行iframe嵌套页面
        http//1、配置权限认证
                .authorizeRequests()
                //配置不拦截路由
                .antMatchers("/500").permitAll()
                .antMatchers("/403").permitAll()
                .antMatchers("/404").permitAll()
                .antMatchers("/goLogin.do").permitAll()
                .antMatchers("/login.do").permitAll()
                .anyRequest() //任何其它请求
                .authenticated() //都需要身份认证
                .and()
                //2、登录配置表单认证方式
                .formLogin()
                .loginPage("/goLogin.do")//自定义登录页面的url
                .usernameParameter("username")//设置登录账号参数，与表单参数一致
                .passwordParameter("password")//设置登录密码参数，与表单参数一致
                // 告诉Spring Security在发送指定路径时处理提交的凭证，默认情况下，将用户重定向回用户来自的页面。登录表单form中action的地址，也就是处理认证请求的路径，
                // 只要保持表单中action和HttpSecurity里配置的loginProcessingUrl一致就可以了，也不用自己去处理，它不会将请求传递给Spring MVC和您的控制器，所以我们就不需要自己再去写一个/user/login的控制器接口了
                .loginProcessingUrl("/login.do")//配置默认登录入口
                .defaultSuccessUrl("/goIndex.do")//登录成功后默认的跳转页面路径
                .failureUrl("/goLogin.do?error=true")
                .and()
                //3、注销
                .logout()
                .logoutUrl("/logout.do")
                .permitAll()
                .and()
                //4、session管理
                .sessionManagement()
                .invalidSessionUrl("/login.html") //失效后跳转到登陆页面
                //单用户登录，如果有一个登录了，同一个用户在其他地方登录将前一个剔除下线
                //.maximumSessions(1).expiredSessionStrategy(expiredSessionStrategy())
                //单用户登录，如果有一个登录了，同一个用户在其他地方不能登录
                //.maximumSessions(1).maxSessionsPreventsLogin(true) ;
                .and()
                //5、禁用跨站csrf攻击防御
                .csrf()
                .disable();
    }

}
