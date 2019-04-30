package com.javaweb.dao.user;

import com.javaweb.pojo.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: project_parent
 * @description: 角色权限dao
 * @author: YuKai Fan
 * @create: 2019-04-30 11:12
 **/
public interface RolePermissionDao extends JpaRepository<RolePermission, String> {
}