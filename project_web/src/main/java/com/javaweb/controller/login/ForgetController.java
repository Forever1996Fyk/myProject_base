package com.javaweb.controller.login;

import com.javaweb.entity.Result;
import com.javaweb.enums.ResultEnum;
import com.javaweb.pojo.User;
import com.javaweb.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: project_parent
 * @description: 忘记密码controller
 * @author: YuKai Fan
 * @create: 2019-05-16 09:45
 **/
@RestController
public class ForgetController {
    @Autowired
    private UserService userService;

    /**
     * 判断用户手机是否存在,发送手机验证码
     * @param map
     * @return
     */
    @RequestMapping(value = "/verify/phone", method = RequestMethod.POST)
    public Result verifyPhone(Map<String, Object> map) {
        //判断手机号是否存在
        List<User> userList = userService.findByParam(map);
        if (userList.size() < 0) {
            return new Result(false, ResultEnum.USER_PHONE_NOT_EXIST.getValue(), ResultEnum.USER_PHONE_NOT_EXIST.getMessage());
        }
        //todo 发送验证码
        return new Result(true, ResultEnum.SUCCESS.getValue(), "验证码发送" + ResultEnum.SUCCESS.getMessage());
    }
}