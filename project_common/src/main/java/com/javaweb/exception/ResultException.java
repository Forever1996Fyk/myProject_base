package com.javaweb.exception;

import com.javaweb.enums.ResultEnum;

/**
 * @program: project_parent
 * @description: 结果异常处理
 * @author: YuKai Fan
 * @create: 2019-05-13 16:46
 **/
public class ResultException extends RuntimeException {

    private Integer code;

    public ResultException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getValue();
    }

    public ResultException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}