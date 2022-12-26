package com.lyffin.springcloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storage implements Serializable {
    private Long id;
    private Integer total;
    private Integer used;
    private Integer residue;
}
