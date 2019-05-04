package com.javaweb.service.login;/**
 * Created by 恺b on 2019/5/2.
 */

import com.javaweb.pojo.User;

/**
 * @program: project_parent
 * @description: 登录专用接口
 * @author: Yukai Fan
 * @create: 2019-05-02 17:17
 **/
public interface LoginService {

    /**
     * 根据账号，查询用户
     * @param account
     * @return
     */
    User findUserByAccount(String account);
}
