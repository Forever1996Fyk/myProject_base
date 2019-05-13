package com.javaweb.shiro;/**
 * Created by YuKaiFan on 2019/5/2.
 */

import com.javaweb.properties.ShiroProperties;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
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
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //必须设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //验证过滤器
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        //分布式中可以是用JWT filtersMap.put("jwt", new JWTFilter());
        filtersMap.put("auth", new ShiroFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/static/layuiadmin/**", "anon");
        filterChainDefinitionMap.put("/static/layuiadmin/js/**", "anon");
        filterChainDefinitionMap.put("/static/layuiadmin/images/**", "anon");
        filterChainDefinitionMap.put("/static/layuiadmin/layui/**", "anon");
        filterChainDefinitionMap.put("/static/layuiadmin/lib/**", "anon");
        filterChainDefinitionMap.put("/static/layuiadmin/modules/**", "anon");
        filterChainDefinitionMap.put("/static/layuiadmin/style/**", "anon");
        filterChainDefinitionMap.put("/static/layuiadmin/tpl/**", "anon");

        //访问错误页面不需要通过filter anon表示权限通过
        filterChainDefinitionMap.put("/401", "anon");
        filterChainDefinitionMap.put("/402", "anon");

        //其他过滤器
        filterChainDefinitionMap.put("/user/**", "auth");
        filterChainDefinitionMap.put("/permission/**", "auth");
        filterChainDefinitionMap.put("/role/**", "auth");
        filterChainDefinitionMap.put("/menu", "auth");
        filterChainDefinitionMap.put("/system/**", "auth");

        shiroFilterFactoryBean.setLoginUrl("/");
        // 未授权错误页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }

    /**
     * 定义安全管理器
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm shiroRealm, DefaultWebSessionManager sessionManager, CookieRememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(shiroRealm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(rememberMeManager);

//        //注入缓存管理器
//        securityManager.setCacheManager(ehCacheManager());
//
        //关闭shiro自带的session
//        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
//        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
//        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
//        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
//        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    /**
     * 自定义身份认证realm (账号密码校验;权限等)
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm(EhCacheManager ehCacheManager) {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCacheManager(ehCacheManager);
        return shiroRealm;
    }

    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return ehCacheManager;
    }

    /**
     * session管理器
     * @param ehCacheManager
     * @param shiroProperties
     * @return
     */
    @Bean
    public DefaultWebSessionManager defaultWebSessionManager(EhCacheManager ehCacheManager, ShiroProperties shiroProperties) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(ehCacheManager);
        sessionManager.setGlobalSessionTimeout(shiroProperties.getGlobalSessionTimeout() * 1000);
        sessionManager.setSessionValidationInterval(shiroProperties.getSessionValidationInterval() * 1000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.validateSessions();
        //去掉登录页面地址栏jssessionid
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * rememberMe管理器
     * @param simpleCookie
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie simpleCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey(Base64.decode("WcfHGU25gNnTxTlmJMeSpw=="));
        cookieRememberMeManager.setCookie(simpleCookie);
        return cookieRememberMeManager;
    }

    /**
     * 创建一个简单的Cookie对象
     * @param shiroProperties
     * @return
     */
    @Bean
    public SimpleCookie simpleCookie(ShiroProperties shiroProperties) {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        //cookie记住登录信息时间,默认7天
        simpleCookie.setMaxAge(shiroProperties.getRememberMeTimeout() * 24 * 60 * 60);
        return simpleCookie;
    }

    /**
     * 开启shiro aop注解支持.使用代理方式;所以需要开启代码支持；必须要开启注解才能使用@RequiresPermissions等注解
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
