package com.javaweb.controller.login;/**
 * Created by YuKaiFan on 2019/5/2.
 */

import com.javaweb.entity.Result;
import com.javaweb.entity.StatusCode;
import com.javaweb.enums.ResultEnum;
import com.javaweb.pojo.User;
import com.javaweb.service.login.LoginService;
import com.javaweb.util.shiro.ShiroKit;
import com.javaweb.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * @param userParam 用户账号密码
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody(required = false) UserVo userParam) {

        String account = userParam.getAccount();
        String password = userParam.getPassword();
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            return new Result(false, StatusCode.LOGINERROR.getValue(), ResultEnum.USER_NAME_PWD_NULL.getMessage());
        }
        //获取subject主体对象
        Subject subject = SecurityUtils.getSubject();

        User user = loginService.findUserByAccount(account);

        if (user == null) {
            return new Result(false, StatusCode.LOGINERROR.getValue(), "login failed");
        }

        // 获取盐
        String salt = user.getSalt();
        //原密码加密(通过account + salt作为盐)
        String encodedPasswd = ShiroKit.md5(password, account + salt);
        if (user.getPassword().equals(encodedPasswd)) {
            //封装用户数据
            UsernamePasswordToken token = new UsernamePasswordToken(account, encodedPasswd);
            //登录,进入自定义realm类
            try {
                //判断是否记住账号密码,是否自动登录
                if (userParam.getRememberMe() != null) {
                    token.setRememberMe(true);
                } else {
                    token.setRememberMe(false);
                }
                //自定义reaml操作数据库
                subject.login(token);

                return new Result(true, StatusCode.OK.getValue(), "login success");
            } catch (LockedAccountException e) {
                return new Result(false, StatusCode.LOGINERROR.getValue(), "该账号已被冻结");
            } catch (AuthenticationException e) {
                return new Result(false, StatusCode.LOGINERROR.getValue(), "用户名或密码错误");
            }
        } else {
            return new Result(false, StatusCode.LOGINERROR.getValue(), "login failed");
        }

    }

    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public void logout() {
        SecurityUtils.getSubject().logout();
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
