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
@RequestMapping("/base")
public class BaseController {

    @RequestMapping("/")
    public String index() {
        return "redirect:views/index.html";
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String test() {
        return "index";
    }
}