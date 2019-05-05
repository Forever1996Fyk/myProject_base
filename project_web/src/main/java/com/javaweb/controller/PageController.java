package com.javaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: project_parent
 * @description: 返回页面
 * @author: YuKai Fan
 * @create: 2019-05-05 11:35
 **/
@Controller
public class PageController {

    @RequestMapping("/index")
    public String index() {
        return "views/index";
    }

    @RequestMapping("/console")
    public String console() {
        return "views/home/console";
    }

    @RequestMapping("/userManager")
    public String user() {
        return "views/user/user/list";
    }

    @RequestMapping("/roleManager")
    public String role() {
        return "views/user/user/role";
    }

    @RequestMapping("/premissionManager")
    public String premission() {
        return "views/user/user/permission";
    }

    @RequestMapping("/userManager/userform")
    public String userform() {
        return "views/user/user/userform";
    }

    @RequestMapping("/roleManager/roleform")
    public String roleform() {
        return "views/user/user/roleform";
    }

    @RequestMapping("/premissionManager/permissionform")
    public String permissionform() {
        return "views/user/user/permissionform";
    }

    @RequestMapping("/roleManager/assignRole")
    public String assignRole() {
        return "views/user/user/assignRole";
    }

    @RequestMapping("/authorization")
    public String authorization() {
        return "views/user/user/authorization";
    }
}