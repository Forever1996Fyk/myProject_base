package com.javaweb.controller.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: project_parent
 * @description: 手机验证码
 * @author: YuKai Fan
 * @create: 2019-05-08 14:55
 **/
@RestController
public class smsController {

    @RequestMapping("/auth/code")
    public String sms() {
        return "发送成功";
    }
}