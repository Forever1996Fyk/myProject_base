package com.javaweb.controller.login;

import com.javaweb.entity.Result;
import com.javaweb.enums.ResultEnum;
import com.javaweb.pojo.User;
import com.javaweb.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    private String param;

    /**
     * 判断用户手机是否存在,发送手机验证码
     * @param phone
     * @return
     */
    @RequestMapping(value = "/verify/phone", method = RequestMethod.GET)
    public Result verifyPhone(String phone) {
        
        //判断手机号是否存在
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        List<User> userList = userService.findByParam(map);
        if (userList.size() < 0) {
            return new Result(false, ResultEnum.USER_PHONE_NOT_EXIST.getValue(), ResultEnum.USER_PHONE_NOT_EXIST.getMessage());
        }
        param = map.get("phone").toString();
        //todo 发送验证码
        return new Result(true, ResultEnum.SUCCESS.getValue(), "验证码发送" + ResultEnum.SUCCESS.getMessage());
    }


    @RequestMapping(value = "/resetPwd", method = RequestMethod.PUT)
    public Result resetPwd(@RequestBody User userParam) {
        if (param == null) {
            return new Result(false, ResultEnum.USER_PHONE_NOT_EXIST.getValue(), ResultEnum.USER_PHONE_NOT_EXIST.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone", param);
        List<User> userList = userService.findByParam(map);
        if (userList.size() > 0) {
            User user = userList.get(0);
            user.setPassword(userParam.getPassword());
            userService.add(user);
            return new Result(true, ResultEnum.SUCCESS.getValue(), "密码修改" + ResultEnum.SUCCESS.getMessage());
        }
        return new Result(false, ResultEnum.USER_PHONE_NOT_EXIST.getValue(), ResultEnum.USER_PHONE_NOT_EXIST.getMessage());
    }
}