package com.javaweb.util.jwt;/**
 * Created by 恺b on 2019/5/2.
 */

import com.javaweb.entity.StatusCode;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: project_parent
 * @description: 重写filter。所有请求都会经过Filter,所以继承官方的BasicHttpAuthenticationFilter,并重写鉴定权限的方法;
 *              另外通过重写preHandle,实现跨越访问。
 *              代码执行流程：preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 * @author: Yukai Fan
 * @create: 2019-05-02 21:16
 **/
public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     * 判断用户是否想要登入
     * 检测header里面是否包含Authorization字段即可
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        return authorization != null;
    }

    /**
     * 通过realm进行登陆操作
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        JWTToken token = new JWTToken(authorization);
        //提交给realm进行登录,如果错误会抛出异常并被捕获
        getSubject(request, response).login(token);
        //如果没有抛出异常则代表登陆成功,返回true
        return true;
    }

    /**
     *  判断请求是否允许访问
     *  假如有个请求地址GET /artice 登陆用户和游客看到的内容是不同的
     *  如果这个方法返回false,请求会被直接拦截,用户看不到任何东西
     *  所以这个方法一直返回true,Controller中可以通过subject.isAuthenticated()来判断用户是否登陆
     *  如果有些资源只有登陆用户才能访问,只需要在方法上加上@requiresAuthentication注解即可
     *  但是这种方式的缺点就是不能对GET,POST等请求进行分别过滤(因为重写官方的方法),但是实际影响不大
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {

            }
        }
        return true;
    }

    /**
     * 跨域提供支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-control-Allow-Origin", req.getHeader("Origin"));
        resp.setHeader("Access-control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        resp.setHeader("Access-control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));

        //跨域时会首先发送一个option请求,直接返回正常状态
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            resp.setStatus(StatusCode.OK.getValue());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /401页面
     * @param request
     * @param response
     */
    private void response401(ServletRequest request, ServletResponse response) {
        HttpServletResponse resp = (HttpServletResponse) response;
        try {
            resp.sendRedirect("/401");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
