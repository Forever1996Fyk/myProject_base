package com.javaweb.controller.user;

import com.javaweb.pojo.Permission;
import com.javaweb.service.user.PermissionService;
import com.javaweb.entity.PageResult;
import com.javaweb.entity.Result;
import com.javaweb.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.javaweb.util.IdWorker;

import java.util.Map;

/**
 * @program: project_parent
 * @description: 权限管理
 * @author: YuKai Fan
 * @create: 2019-04-24 15:55
 **/
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有用户
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true,  StatusCode.OK.getValue(), "查询成功", permissionService.findAll());
    }

    /**
     * 新增用户
     * @param permission
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Permission permission) {
        permission.setId(String.valueOf(idWorker.nextId()));
        permission.setStatus(1);
        return new Result(true,  StatusCode.OK.getValue(), "新增成功", permissionService.add(permission));
    }

    /**
     * 更新用户
     * @param permission
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody Permission permission) {
        return new Result(true,  StatusCode.OK.getValue(), "更新成功", permissionService.update(permission));
    }

    /**
     * 删除用户(这里是软删除，更改状态)
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        permissionService.delete(id);
        return new Result(true,  StatusCode.OK.getValue(), "删除成功");
    }
    /**
     * 分页+多条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Result pageQuery(@RequestParam Map<String, Object> searchMap) {
        Page<Permission> pageData = permissionService.pageQuery(searchMap, Integer.parseInt(searchMap.get("page").toString()), Integer.parseInt(searchMap.get("limit").toString()));
        return new Result(true, StatusCode.OK.getValue(), "查询成功", new PageResult<Permission>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 批量删除用户(这里是软删除，更改状态)
     * @param idsMap
     * @return
     */
    @RequestMapping(value = "/delBatch", method = RequestMethod.DELETE)
    public Result delBatch(@RequestParam Map<String, Object> idsMap) {
        permissionService.delBatch(idsMap);
        return new Result(true,  StatusCode.OK.getValue(), "删除成功");
    }

}