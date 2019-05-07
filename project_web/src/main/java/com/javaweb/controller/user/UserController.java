package com.javaweb.controller.user;

import com.javaweb.pojo.Role;
import com.javaweb.pojo.User;
import com.javaweb.service.user.UserService;
import com.javaweb.enums.StatusEnum;
import com.javaweb.entity.PageResult;
import com.javaweb.entity.Result;
import com.javaweb.entity.StatusCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.javaweb.util.IdWorker;

import java.util.HashSet;
import java.util.Map;

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
    public Result add(@RequestBody User user) {
        user.setId(String.valueOf(idWorker.nextId()));
        user.setStatus(StatusEnum.Normal.getValue());
        return new Result(true,  StatusCode.OK.getValue(), "新增成功", userService.add(user));
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody User user) {
        return new Result(true,  StatusCode.OK.getValue(), "更新成功", userService.update(user));
    }

    /**
     * 删除用户(这里是软删除，更改状态)
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        userService.delete(id);
        return new Result(true,  StatusCode.OK.getValue(), "删除成功");
    }

    /**
     * 分页+多条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Result pageQuery(@RequestParam Map<String, Object> searchMap) {
        Page<User> pageData = userService.pageQuery(searchMap, Integer.parseInt(searchMap.get("page").toString()), Integer.parseInt(searchMap.get("limit").toString()));
        return new Result(true, StatusCode.OK.getValue(), "查询成功", new PageResult<User>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 批量删除用户(这里是软删除，更改状态)
     * @param idsMap
     * @return
     */
    @RequestMapping(value = "/delBatch", method = RequestMethod.DELETE)
    public Result delBatch(@RequestParam Map<String, Object> idsMap) {
        userService.delBatch(idsMap);
        return new Result(true,  StatusCode.OK.getValue(), "删除成功");
    }

    /**
     * 获取用户的角色
     * @param user
     * @return
     */
    @RequestMapping(value = "/findRole", method = RequestMethod.POST)
    public Result findRole(@RequestBody User user) {
        return new Result(true, StatusCode.OK.getValue(), "查询成功", userService.findRole(user));
    }

    /**
     * 获取用户的角色
     * @param user
     * @return
     */
    @RequestMapping(value = "/saveRole", method = RequestMethod.POST)
    public Result saveRole(@RequestParam(value = "id", required = true) User user,
                           @RequestParam(value = "roleId", required = false) HashSet<Role> roles) {
        user.setRoles(roles);
        userService.update(user);
        return new Result(true, StatusCode.OK.getValue(), "保存成功");
    }
}