package com.lyffin.springcloud.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {


    private String code;


    private String msg;


    private T data;


    public Result(String code,String msg){
        this.code = code;
        this.msg = msg;
    }


    /**
     * 无参数的成功返回类
     * @return
     */
    public static Result success(){
        return new Result(ResultCodeEnums.SUCCESS.getCode(), ResultCodeEnums.SUCCESS.getMsg());
    }


    /**
     * 有参数的成功返回类
     * @param object
     * @return
     */
    public static Result success(Object object){
        return new Result(ResultCodeEnums.SUCCESS.getCode(), ResultCodeEnums.SUCCESS.getMsg(),object);
    }

    /**
     * 失败返回类
     * @return
     */
    public static Result failed() {
        return new Result(ResultCodeEnums.ERROR.getCode(), ResultCodeEnums.ERROR.getMsg());
    }

}
