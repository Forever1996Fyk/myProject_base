package com.javaweb.service.user;

import com.javaweb.pojo.Permission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @program: project_parent
 * @description:
 * @author: YuKai Fan
 * @create: 2019-04-24 16:11
 **/
public interface PermissionService {

    /**
     * 获取全部权限
     * @return
     */
    List<Permission> findAll();

    /**
     * 新增权限
     * @return
     */
    Permission add(Permission permission);

    /**
     * 更新权限
     * @param permission
     * @return
     */
    Permission update(Permission permission);

    /**
     * 删除权限
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
    Page<Permission> pageQuery(Map<String, Object> searchMap, int page, int size);

    /**
     * 批量删除
     * @param idsMap
     */
    void delBatch(Map<String, Object> idsMap);
}