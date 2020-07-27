package com.jt.vo;

import javafx.beans.binding.ObjectExpression;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

//系统级VO对象
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysResult implements Serializable {
    private Integer status;
    private String msg;
    private Object data;

    public static SysResult fail() {
        return new SysResult(201, "业务调用失败", null);
    }

    public static SysResult success() {
        return new SysResult(200, "业务调用成功", null);
    }

    public static SysResult success(Object data) {
        return new SysResult(200, "业务调用成功", data);
    }

    public static SysResult success(String msg, Object data) {
        return new SysResult(200, msg, data);
    }

}
