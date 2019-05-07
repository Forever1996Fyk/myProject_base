package com.javaweb.shiro;/**
 * Created by YuKai Fan on 2019/5/2.
 */

import com.javaweb.constant.Constant;
import com.javaweb.enums.StatusEnum;
import com.javaweb.pojo.Permission;
import com.javaweb.pojo.Role;
import com.javaweb.pojo.User;
import com.javaweb.service.login.LoginService;
import com.javaweb.service.user.RoleService;
import com.javaweb.util.jwt.JWTToken;
import com.javaweb.util.jwt.JWTUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: project_parent
 * @description: 身份校验核心类
 * @author: Yukai Fan
 * @create: 2019-05-02 20:08
 **/
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private LoginService loginService;
    @Autowired
    private RoleService roleService;

    /**
     * 必须重写此方法，不然shiro会报错
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 认证信息(身份验证)
     * @param auth 是用来验证用户身份, 认证用户提交的信息
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) auth;

        //获取JWT token
//        String token = (String) auth.getCredentials();
//        //根据token获取account,用于和数据库进行对比
//        String account = JWTUtil.getAccount(token);
//        if (account == null) {
//            throw new AuthenticationException("token invalid! token无效");
//        }

        //通过account从数据库中查找user对象
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro也是有时间间隔机制，2分钟不会重复执行该方法
        //todo 从缓存中取出
        User user = loginService.findUserByAccount(token.getUsername());

        if (user == null) {
            throw new UnknownAccountException("用户账号错误");
        } else if (!user.getStatus().equals(StatusEnum.Normal.getValue())) {
            throw new LockedAccountException("用户账号已无效");
        }

        //对盐进行加密处理
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());

        return new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());//第一个参数：对象主体;第二个参数：对象凭据;第三个参数：realm名称
    }

    /**
     *  此方法调用hasRole,hasPermission时才会进行回调
     *  <p>
     *  权限信息(授权) ：
     *  1.如果用户正常退出,缓存自动清空
     *  2.如果用户非正常退出,缓存自动清空
     *  3.如果我们修改了用户的权限,而用户不退出系统,修改的权限无法立即生效。
     *  (需要手动编程进行实现;放在service进行调用)
     *  在权限修改后调用realm中的方法,realm已经有spring管理,所以从spring中获取realm实例,调用clearCached方法;
     *  Authorization是授权访问控制,用用于对用户进行的操作授权,证明该用户是否允许进行当前操作。如访问某个链接，某个资源文件等。
     *  </p>
     *
     *
     * @param principals PrincipalCollection：是一个身份集合,因为我们可以在Shiro中同事配置多个realm,所以身份信息可能就有多个
     *                   该集合就是用于聚合这些身份信息
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /**
         * 当没有使用缓存时,不断刷新页面,这个代码会不断执行,
         * 其实没有必要每次都重新设置权限信息,所有我们需要放到缓存进行管理
         * 当将用户凭证信息放入缓存中,doGetAuthorizationInfo就只会执行一次
         * 缓存过期之后会再次执行
         *
         */

        //todo 下面代码中，用户信息可以从缓存中取出
        //获取用户principals对象
        User user = (User) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
        //管理员拥有所有权限
        if (user.getId().equals(Constant.ADMIN_ID)) {
            auth.addRole(Constant.ADMIN_ROLE_NAME);
            auth.addStringPermission("*:*:*");//这个对应了@RequiresPermissions('xxx')注解上的名称,只有与该名称匹配上,才有该权限。*:*:*，表示所有权限
            return auth;
        }

        //设置相应角色的权限信息
        for (Role role : user.getRoles()) {
            //设置角色
            auth.addRole(role.getRole_name());
            for (Permission permission : role.getPermissions()) {
                auth.addStringPermission(permission.getPerm());//注意:perm这个字段不能有null,否则访问请求会报错
            }
        }

        return auth;

    }

}
