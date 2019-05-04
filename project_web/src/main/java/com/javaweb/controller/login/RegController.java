package com.javaweb.controller.login;/**
 * Created by 恺b on 2019/5/3.
 */

import com.javaweb.entity.Result;
import com.javaweb.entity.StatusCode;
import com.javaweb.pojo.User;
import com.javaweb.service.user.UserService;
import com.javaweb.util.IdWorker;
import com.javaweb.util.MapUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: project_parent
 * @description: 注册
 * @author: YuKai Fan
 * @create: 2019-05-03 22:01
 **/
@RestController
public class RegController {
    @Autowired
    private UserService userService;
    @Autowired
    private IdWorker idWorker;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestBody User user) {
        //判断账号是否已存在
        Map<String, Object> map = new HashMap<>();
        map.put("account", user.getAccount());
        List<User> userList = userService.findByParam(map);
        if (userList.size() > 0) {
            return new Result(false, StatusCode.ERROR.getValue(), "注册失败,该账号已存在!");
        }
        user.setId(String.valueOf(idWorker.nextId()));
        userService.add(user);
        return new Result(true, StatusCode.OK.getValue(), "注册成功");
    }

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @RequestMapping(value = "/sendCode/{phone}", method = RequestMethod.GET)
    public Result sendCode(@PathVariable("phone") String phone) {
        //todo
        return new Result(true, StatusCode.OK.getValue(), "发送成功");
    }
}
