package com.javaweb.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: project_parent
 * @description: 文件controller
 * @author: YuKai Fan
 * @create: 2019-05-09 15:13
 **/
@Controller
@RequestMapping("/api")
public class AttachController {



    @RequestMapping("/upload/{type}")
    public void uploadAttach(@PathVariable String type) {

    }


}