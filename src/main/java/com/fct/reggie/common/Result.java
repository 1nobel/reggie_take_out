package com.fct.reggie.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
@ApiModel("返回结果信息")
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("返回数字1成功0失败")
    private Integer code;

    @ApiModelProperty("结果数据信息")
    private Object data;

    @ApiModelProperty("提示信息")
    private String msg;

    @ApiModelProperty("动态数据")
    private Map map = new HashMap();//动态数据
//    正确时使用的方法
    public static<T> Result<T> success(Integer code,Object data) {
        Result<T> r = new Result<T>();
        r.code = code;
        r.data = data;
        return r;
    }
//    错误时使用的方法
    public static<T> Result<T> error(Integer code,String msg){
        Result<T> r = new Result<T>();
        r.code = code;
        r.msg = msg;
        return r;
    }

    public Result<T> add(String key,Object value){
        this.map.put(key,value);
        return this;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
