package com.javaweb.service.login;/**
 * Created by 恺b on 2019/5/2.
 */

import com.javaweb.dao.user.UserDao;
import com.javaweb.pojo.User;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

/**
 * @program: project_parent
 * @description: 登录实现类
 * @author: Yukai Fan
 * @create: 2019-05-02 17:20
 **/
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDao userDao;
    @Override
    public User findUserByAccount(String account) {
        List<User> userList = userDao.findByAccount(account);
        if (userList.size() <= 0 && userList == null) {
            throw new UnknownAccountException();
        }
        return userList.get(0);
    }
}
