package com.javaweb.controller.base;

import com.javaweb.constant.Constant;
import com.javaweb.entity.Result;
import com.javaweb.entity.StatusCode;
import com.javaweb.enums.MenuTypeEnum;
import com.javaweb.pojo.Permission;
import com.javaweb.pojo.Role;
import com.javaweb.pojo.User;
import com.javaweb.service.user.PermissionService;
import com.javaweb.util.shiro.ShiroKit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: project_parent
 * @description: 首页controller
 * @author: YuKai Fan
 * @create: 2019-05-05 14:00
 **/
@RestController
public class IndexController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/menu")
    @RequiresPermissions("system:index")
    public Result menu() {
        //获取当前登录用户
        User curUser = (User) ShiroKit.getSubject().getPrincipal();

        //菜单键值对
        Map<String, Permission> keyMenu = new HashMap<>();

        //如果是超级管理员,则实时更新菜单
        if (curUser.getId().equals(Constant.ADMIN_ID)) {
            List<Permission> permissionList = permissionService.findAll();
            for (Permission permission : permissionList) {
                keyMenu.put(permission.getId(), permission);
            }
        } else {//其他用户从相应的角色中获取菜单
            for (Role role : curUser.getRoles()) {
                for (Permission permission : role.getPermissions()) {
                    keyMenu.put(permission.getId(), permission);
                }
            }
        }

        //封装菜单树形数据
        Map<String, Object> treeMap = new HashMap<>();
        for (Map.Entry<String, Permission> entry : keyMenu.entrySet()) {
            Permission permission = entry.getValue();
            if (!permission.getLevel().equals(MenuTypeEnum.NOT_MENU.getValue())) {//如果是菜单
                if (keyMenu.get(permission.getPid()) != null) {//如果pid不为空,就以排序号为key, permission为值
                    keyMenu.get(permission.getPid()).getChildren().put(String.valueOf(permission.getSort()), permission);
                } else {
                    if (permission.getLevel().equals(MenuTypeEnum.TOP_LEVEL.getValue())) {//如果是一级菜单
                        treeMap.put(String.valueOf(permission.getSort()), permission);
                    }
                }
            } else {
                if (keyMenu.get(permission.getPid()) != null) {//如果pid不为空,就以排序号为key, permission为值
                    keyMenu.get(permission.getPid()).getChildren().put(String.valueOf(permission.getSort()), permission);
                }
            }

        }

        Map<String, Object> map = new HashMap<>();
        map.put("user", curUser);
        map.put("treeMap", treeMap);

        return new Result(true, StatusCode.OK.getValue(), "获取成功", map);
    }
}