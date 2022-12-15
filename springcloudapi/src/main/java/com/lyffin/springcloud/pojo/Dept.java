package com.lyffin.springcloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

//所有实体类务必实现序列化
@Data
@NoArgsConstructor
@Accessors(chain = true) //链式写法
public class Dept implements Serializable {

    private long deptno;
    private String dname;
    //微服务架构中，可以一个服务对应一个数据库
    private String db_source;

    public Dept(String dname) {
        this.dname = dname;
    }

    /**
     * 链式写法
     * Dept dept = new Dept();
     * dept.setDname("lyffin").setDeptno(111 );
     */
}
