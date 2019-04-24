package com.javaweb.dao.user;

import com.javaweb.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @program: project_parent
 * @description:
 * @author: YuKai Fan
 * @create: 2019-04-24 16:14
 **/
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User>{
}