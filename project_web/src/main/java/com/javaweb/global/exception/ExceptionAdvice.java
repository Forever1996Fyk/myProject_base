package com.javaweb.global.exception;/**
 * Created by 恺b on 2019/4/25.
 */

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: project_parent
 * @description: 统一异常处理
 * @author: Yukai Fan
 * @create: 2019-04-25 20:22
 **/
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        return new Result(false, StatusCode.ERROR.getValue(), "异常错误: {" + e.getMessage() + "}");
    }
}
