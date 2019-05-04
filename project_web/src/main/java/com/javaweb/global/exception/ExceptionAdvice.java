package com.javaweb.global.exception;/**
 * Created by 恺b on 2019/4/25.
 */

import com.javaweb.entity.Result;
import com.javaweb.entity.StatusCode;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: project_parent
 * @description: 统一异常处理
 * @author: Yukai Fan
 * @create: 2019-04-25 20:22
 **/
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 其他全局异常处理
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Throwable ex) {
        return new Result(false, StatusCode.ERROR.getValue(), "异常错误: {" + ex.getMessage() + "}");
    }

    /**
     * 捕获shiro异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result handle401(ShiroException e) {
        return new Result(false, StatusCode.ACCESSERROR.getValue(), "shiro异常", null);
    }

    /**
     * 捕获UnauthorizedException
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public Result handle41() {
        return new Result(false, StatusCode.LOGINERROR.getValue() ,"UnauthrizedException 异常", null);
    }

    /**
     * 获取http状态码
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.valueOf(statusCode);
    }
}
