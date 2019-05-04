package com.javaweb.global.exception;/**
 * Created by 恺b on 2019/5/2.
 */

/**
 * @program: project_parent
 * @description: 重写UnauthorizedException
 * @author: Yukai Fan
 * @create: 2019-05-02 17:57
 **/
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}
