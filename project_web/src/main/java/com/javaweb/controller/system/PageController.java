package com.javaweb.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: project_parent
 * @description: 返回页面
 * @author: YuKai Fan
 * @create: 2019-05-05 11:35
 **/
@Controller
@RequestMapping("/system")
public class PageController {

    @RequestMapping("/index")
    @RequiresPermissions("system:index")
    public String index() {
        return "views/index";
    }

    @RequestMapping("/console")
    public String console() {
        return "views/home/console";
    }

    @RequestMapping("/userManager")
    @RequiresPermissions("system:userManager")
    public String user() {
        return "views/user/user/list";
    }

    @RequestMapping("/roleManager")
    @RequiresPermissions("system:roleManager")
    public String role() {
        return "views/user/user/role";
    }

    @RequestMapping("/premissionManager")
    @RequiresPermissions("system:premissionManager")
    public String premission() {
        return "views/user/user/permission";
    }

    @RequestMapping("/userManager/userform")
    @RequiresPermissions("system:userManager:userform")
    public String userform() {
        return "views/user/user/userform";
    }

    @RequestMapping("/roleManager/roleform")
    @RequiresPermissions("system:roleManager:roleform")
    public String roleform() {
        return "views/user/user/roleform";
    }

    @RequestMapping("/premissionManager/permissionform")
    @RequiresPermissions("system:premission:permissionform")
    public String permissionform() {
        return "views/user/user/permissionform";
    }

    @RequestMapping("/roleManager/assignRole")
    @RequiresPermissions("system:roleManager:assignRole")
    public String assignRole() {
        return "views/user/user/assignRole";
    }

    @RequestMapping("/authorization")
    @RequiresPermissions("system:authorization")
    public String authorization() {
        return "views/user/user/authorization";
    }

    @RequestMapping("/userInfo")
    public String userInfo() {
        return "views/user/info";
    }
}