package com.javaweb.util.jwt;/**
 * Created by 恺b on 2019/5/2.
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @program: project_parent
 * @description: jwt加密解密工具类
 * @author: Yukai Fan
 * @create: 2019-05-02 17:26
 **/
public class JWTUtil {
    //过期时间5分钟
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 生成签名,5min后过期
     * @param account 用户账号
     * @param pwd 用户密码
     * @return
     */
    public static String sign(String account, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //附带account信息
        return JWT.create()
                .withClaim("account", account)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 校验token是否正确
     * @param token
     * @param account
     * @param secret
     * @return
     */
    public static boolean verify(String token, String account, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("account", account)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return true;
    }

    /**
     * 或者token中的信息无需密码secret也能获得
     * @param token
     * @return token中包含的用户名
     */
    public static String getAccount(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("account").asString();
    }
}
