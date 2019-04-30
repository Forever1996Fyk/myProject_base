package com.javaweb.service.user;

import com.javaweb.pojo.Permission;
import com.javaweb.pojo.Role;
import com.javaweb.pojo.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @program: project_parent
 * @description:
 * @author: YuKai Fan
 * @create: 2019-04-24 16:11
 **/
public interface RoleService {

    /**
     * 获取全部用户
     * @return
     */
    List<Role> findAll();

    /**
     * 新增用户
     * @return
     */
    Role add(Role role);

    /**
     * 更新用户
     * @param role
     * @return
     */
    Role update(Role role);

    /**
     * 删除用户
     * @param id
     * @return
     */
    void delete(String id);

    /**
     * 分页+ 条件查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    Page<Role> pageQuery(Map<String, Object> searchMap, int page, int size);

    /**
     * 批量删除
     * @param idsMap
     */
    void delBatch(Map<String, Object> idsMap);

    /**
     * 获取权限资源
     * @param role
     * @return
     */
    List<Permission> findPermission(Role role);
}