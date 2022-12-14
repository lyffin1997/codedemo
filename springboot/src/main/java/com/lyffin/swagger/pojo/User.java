package com.lyffin.swagger.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//给swagger的model加注释
@ApiModel(value = "用户实体类")
public class User {

    private int id;
    @ApiModelProperty(value = "用户名")
    private String name;
    @ApiModelProperty(value = "用户密码")
    private String pwd;
    @ApiModelProperty(value = "用户权限等级")
    private String perms;

}
