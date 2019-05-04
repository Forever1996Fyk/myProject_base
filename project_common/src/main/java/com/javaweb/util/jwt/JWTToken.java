package com.javaweb.util.jwt;/**
 * Created by YuKaiFan on 2019/5/2.
 */

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: project_parent
 * @description: shiro用户名密码载体
 * AuthenticationToken代表身份和凭证，返回对象都是object，也就是说Shiro中对身份和凭证的类型没有限制，Shiro没有提供特有的类型来处理
 * @author: Yukai Fan
 * @create: 2019-05-02 20:03
 **/
public class JWTToken implements AuthenticationToken {

    //秘钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
