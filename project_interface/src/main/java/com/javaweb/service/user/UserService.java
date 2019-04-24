package com.javaweb.service.user;

import com.javaweb.pojo.User;

import java.util.List;

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
     * 新增用户
     * @return
     */
    User addUser(User user);
}