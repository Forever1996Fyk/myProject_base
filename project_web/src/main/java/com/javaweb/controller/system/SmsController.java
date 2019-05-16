package com.javaweb.controller.system;

import com.javaweb.entity.Result;
import com.javaweb.enums.ResultEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: project_parent
 * @description: 手机验证码
 * @author: YuKai Fan
 * @create: 2019-05-08 14:55
 **/
@RestController
@RequestMapping("/sms")
public class SmsController {

    @RequestMapping("/auth/code")
    public String sms() {
        return "发送成功";
    }

    /**
     * 验证发送的验证码是否正确
     * @param map
     * @return
     */
    @RequestMapping(value = "/verify/authCode", method = RequestMethod.GET)
    public Result verifyAuthCode(@RequestParam Map<String, Object> map) {
        return new Result(true, ResultEnum.SUCCESS.getValue(), ResultEnum.SUCCESS.getMessage());
    }



}