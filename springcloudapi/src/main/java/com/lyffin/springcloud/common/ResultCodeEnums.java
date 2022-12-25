package com.lyffin.springcloud.common;

import lombok.Getter;

@Getter
public enum ResultCodeEnums {

    SUCCESS("200","成功"),
    ERROR("500","系统繁忙");

    ResultCodeEnums(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private String code;


    private String msg;
}