package com.javaweb.shiro;/**
 * Created by YuKaiFan on 2019/5/2.
 */

import com.javaweb.util.jwt.JWTFilter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: project_parent
 * @description: shiroConfig配置类
 * @author: Yukai Fan
 * @create: 2019-05-02 21:46
 **/
@Configuration
@Order(1)
public class ShiroConfig {

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题
     * 注意：单独一个ShiroFilterFactoryBean配置是报错的,
     * 因为在初始化ShiroFilterFactoryBean时需要注入：SecurityManager
     * Filter Chain定义说明：
     * 1.一个URL可以配置多个Filter,使用逗号分隔
     * 2.当设置多个过滤器,全部验证通过,才视为通过
     * 3.部分过滤器可指定参数,如perms,roles
     *
     *  过滤规则（注意优先级）
     *  —anon 无需认证(登录)可访问
     * 	—authc 必须认证才可访问
     * 	—perms[标识] 拥有资源权限才可访问
     * 	—role 拥有角色权限才可访问
     * 	—user 认证和自动登录可访问
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //必须设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //验证过滤器
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        filtersMap.put("jwt", new JWTFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/static/**", "anon");

        //访问错误页面不需要通过filter anon表示权限通过
        filterChainDefinitionMap.put("/401", "anon");
        filterChainDefinitionMap.put("/402", "anon");

        //其他过滤器
        filterChainDefinitionMap.put("/menu", "jwt");
        filterChainDefinitionMap.put("/user/**", "jwt");
        filterChainDefinitionMap.put("/role/**", "jwt");
        filterChainDefinitionMap.put("/permission/**", "jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }

    /**
     * 定义安全管理器
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(shiroRealm());

        //注入缓存管理器
        securityManager.setCacheManager(ehCacheManager());

        //关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    /**
     * 自定义身份认证realm (账号密码校验;权限等)
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }

    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return ehCacheManager;
    }

    /**
     * 开启shiro aop注解支持.使用代理方式;所以需要开启代码支持；
     * @param securityManager
     * @return 授权Advisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
