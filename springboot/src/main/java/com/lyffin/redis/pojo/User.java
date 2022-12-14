package com.lyffin.redis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
//所有的实体类一般都要序列化，比如redis只有序列化后对象才能存进数据库
public class User implements Serializable {
    private String name;
    private String age;

}
