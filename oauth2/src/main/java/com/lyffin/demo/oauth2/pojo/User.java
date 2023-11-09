package com.lyffin.demo.oauth2.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {
    private Integer id; //用户id
    private String username;    //用户账号
    private String password;    //用户密码
    private String addUser;     //添加用户人员账号
    private String editUser;  //编辑用户人员账号
    private Date addDate;   //添加账号时间
    private Date updateDate;    //更新账号时间
    private Role role;  //用户角色

}
