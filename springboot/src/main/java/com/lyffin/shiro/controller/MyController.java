package com.lyffin.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping({"/","/index"})
    public String toIndex(Model model) {
        //shiro配合前端用法
        //model.addAttribute("msg", "hello,shiro");
        //return "index";
        return "hello,shiro";
    }

    @GetMapping("/user/add")
    public String add() {
        //配合前端
        //return "user/add";
        return "add success!!!!!";
    }

    @GetMapping("/user/update")
    public String update() {
        //配合前端
        //return "user/update";
        return "update success!!!!!";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        //return "login";
        return "please login first!!";
    }

    @GetMapping("/login")
    public String login(String username, String password, Model model) {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //登录
        try {
            subject.login(token);
            //return "index";
            return "登录成功！";
        } catch (UnknownAccountException uae) { //用户名不存在
            //model.addAttribute("msg", "用户名错误");
            //return "login";
            return "用户名错误，请重新登录";
        } catch (IncorrectCredentialsException ice) { //密码错误
            //model.addAttribute("msg", "密码错误");
            //return "login";
            return "密码错误，请重新登录";
        }
        catch (AuthenticationException e) {
            e.printStackTrace();
            return "登录失败";
        }
    }

    @GetMapping("/noauth")
    public String unauthorized() {
        return "账号未授权";
    }

    @GetMapping("/loginOut")
    public String loginOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "登出成功！";
    }
}
