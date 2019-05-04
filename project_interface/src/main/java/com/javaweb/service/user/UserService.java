package com.javaweb.service.user;

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
public interface UserService {

    /**
     * 获取全部用户
     * @return
     */
    List<User> findAll();

    /**
     * 根据条件查询用户
     * @param map
     * @return
     */
    List<User> findByParam(Map<String, Object> map);

    /**
     * 新增用户
     * @return
     */
    User add(User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    User update(User user);

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
    Page<User> pageQuery(Map<String, Object> searchMap, int page, int size);

    /**
     * 批量删除
     * @param idsMap
     */
    void delBatch(Map<String, Object> idsMap);

    /**
     * 查询角色
     * @param user
     * @return
     */
    List<Role> findRole(User user);
}