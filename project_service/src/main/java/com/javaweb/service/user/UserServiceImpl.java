package com.javaweb.service.user;

import com.javaweb.dao.user.UserDao;
import com.javaweb.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: project_parent
 * @description:
 * @author: YuKai Fan
 * @create: 2019-04-24 16:31
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User addUser(User user) {
        return userDao.save(user);
    }
}