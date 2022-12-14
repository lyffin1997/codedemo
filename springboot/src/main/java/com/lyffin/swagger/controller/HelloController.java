package com.lyffin.swagger.controller;

import com.lyffin.swagger.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "hello", tags = "Hello控制类")
@RestController
public class HelloController {

    //给swagger接口中的方法加注释
    @ApiOperation(value = "Hello方法", notes = "返回hello用户名")
    @GetMapping(value = "/hello")
    //value填名称，name填字段名！！！！！
    public String hello(@ApiParam(value = "用户名", name = "username", required = false, defaultValue = "") String username) {
        return "hello swagger" + username;
    }

    @ApiOperation(value = "user方法", notes = "返回user信息")
    //只要接口返回值中存在实体类，swagger页面的model中就会识别
    @PostMapping(value = "/user")
    public User user(@ApiParam(value = "用户信息", name = "user", required = true) User user) {
        return user;
    }
}
