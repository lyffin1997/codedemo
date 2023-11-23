package com.lyffin.security.distributed.uaa.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission implements Serializable {
    private Integer id;     //权限id
    private String name;    //权限名称
    private String keyWord; //权限关键字
    private String description; //权限描述

}
