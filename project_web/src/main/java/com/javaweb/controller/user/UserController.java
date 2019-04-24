package com.javaweb.controller.user;

import com.javaweb.pojo.User;
import com.javaweb.service.user.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import util.IdWorker;

/**
 * @program: project_parent
 * @description: 用户管理
 * @author: YuKai Fan
 * @create: 2019-04-24 15:55
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有用户
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true,  StatusCode.OK.getValue(), "查询成功", userService.findAll());
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addUser(@RequestBody User user) {
        user.setId(String.valueOf(idWorker.nextId()));
        return new Result(true,  StatusCode.OK.getValue(), "新增成功", userService.addUser(user));
    }
}