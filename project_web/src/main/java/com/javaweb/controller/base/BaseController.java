package com.javaweb.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: project_parent
 * @description: 基础模块
 * @author: YuKai Fan
 * @create: 2019-04-24 10:22
 **/
@Controller
@RequestMapping("/")
public class BaseController {

    @RequestMapping("/")
    public String login() {
        return "views/user/login";
    }

    @RequestMapping("/reg")
    public String reg() {
        return "views/user/reg";
    }

    @RequestMapping("/noAuth")
    public String noAuth() {
        return "views/user/error";
    }

//    @RequestMapping("/index")
//    public String index() {
//        return "views/index";
//    }

}