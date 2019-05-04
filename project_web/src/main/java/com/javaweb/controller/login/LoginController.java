package com.javaweb.controller.login;/**
 * Created by 恺b on 2019/5/2.
 */

import com.javaweb.entity.Result;
import com.javaweb.entity.StatusCode;
import com.javaweb.pojo.User;
import com.javaweb.service.login.LoginService;
import com.javaweb.util.jwt.JWTUtil;
import com.javaweb.util.shiro.ShiroKit;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: project_parent
 * @description: 登录
 * @author: Yukai Fan
 * @create: 2019-05-02 15:39
 **/
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     *
     * @param contentType 获取http的头信息，即token
     * @param userParam 用户账号密码
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestHeader(name = "Content-Type", defaultValue = "application/json") String contentType,
                        @RequestBody(required = false) User userParam) {

        String account = userParam.getAccount();
        String password = userParam.getPassword();
        User user = loginService.findUserByAccount(account);
        //获取盐
        String salt = user.getSalt();
        //原密码加密(通过account + salt作为盐)
        String encodedPasswd = ShiroKit.md5(password, account + salt);
        if (user.getPassword().equals(encodedPasswd)) {
            return new Result(true, StatusCode.OK.getValue(), "login success", JWTUtil.sign(account, encodedPasswd));
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     * 生成随机验证码
     * @return
     */
    public Result verifyCode() {
        //todo
        return new Result(true, StatusCode.OK.getValue(), "生成成功");
    }


}
