package com.javaweb.controller.user;

import com.javaweb.pojo.Permission;
import com.javaweb.pojo.Role;
import com.javaweb.service.user.RoleService;
import com.javaweb.constant.StatusConstant;
import com.javaweb.entity.PageResult;
import com.javaweb.entity.Result;
import com.javaweb.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.javaweb.util.IdWorker;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @program: project_parent
 * @description: 角色管理
 * @author: YuKai Fan
 * @create: 2019-04-24 15:55
 **/
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有用户
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true,  StatusCode.OK.getValue(), "查询成功", roleService.findAll());
    }

    /**
     * 新增用户
     * @param role
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Role role) {
        role.setId(String.valueOf(idWorker.nextId()));
        role.setStatus(StatusConstant.Normal.getValue());
        return new Result(true,  StatusCode.OK.getValue(), "新增成功", roleService.add(role));
    }

    /**
     * 更新用户
     * @param role
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody Role role) {
        return new Result(true,  StatusCode.OK.getValue(), "更新成功", roleService.update(role));
    }

    /**
     * 删除用户(这里是软删除，更改状态)
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        roleService.delete(id);
        return new Result(true,  StatusCode.OK.getValue(), "删除成功");
    }

    /**
     * 分页+多条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Result pageQuery(@RequestParam Map<String, Object> searchMap) {
        Page<Role> pageData = roleService.pageQuery(searchMap, Integer.parseInt(searchMap.get("page").toString()), Integer.parseInt(searchMap.get("limit").toString()));
        PageResult<Role> rolePageResult = new PageResult<>(pageData.getTotalElements(), pageData.getContent());
        return new Result(true, StatusCode.OK.getValue(), "查询成功",rolePageResult);
    }

    /**
     * 批量删除用户(这里是软删除，更改状态)
     * @param idsMap
     * @return
     */
    @RequestMapping(value = "/delBatch", method = RequestMethod.DELETE)
    public Result delBatch(@RequestParam Map<String, Object> idsMap) {
        roleService.delBatch(idsMap);
        return new Result(true,  StatusCode.OK.getValue(), "删除成功");
    }

    /**
     * 查询该角色的权限资源
     * @param role
     * @return
     */
    @RequestMapping(value = "/findPermission", method = RequestMethod.POST)
    public Result findPermission(@RequestBody Role role) {
        List<Permission> permission = roleService.findPermission(role);
        return new Result(true, StatusCode.OK.getValue(), "查询成功", permission);
    }

    /**
     * 保存权限
     * @param role
     * @return
     */
    @RequestMapping(value = "/savePermission", method = RequestMethod.POST)
    public Result savePermission(@RequestParam(value = "id", required = true) Role role,
                                 @RequestParam(value = "authId", required = false) HashSet<Permission> permissions) {
        role.setPermissions(permissions);
        roleService.add(role);
        return new Result(true, StatusCode.OK.getValue(), "保存成功");
    }

}