package com.lyffin.security.distributed.uaa.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    private Integer id;     //角色id
    private String name;    //角色名称
    private String keyWord; //角色关键字
    private String description; //角色描述
    private Set<Permission> permissionList;    //用户权限集合
}
