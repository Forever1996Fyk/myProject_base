package com.javaweb.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: project_parent
 * @description:
 * @author: YuKai Fan
 * @create: 2019-05-06 16:58
 **/
@Component
@ConfigurationProperties(prefix = "project.shiro")
public class ShiroProperties {
    // cookie记住登录信息时间，默认7天
    private Integer rememberMeTimeout = 7;
    // Session会话超时时间，默认30分钟
    private Integer globalSessionTimeout = 1800;
    // Session会话检测间隔时间，默认15分钟
    private Integer sessionValidationInterval = 900;

    public Integer getRememberMeTimeout() {
        return rememberMeTimeout;
    }

    public void setRememberMeTimeout(Integer rememberMeTimeout) {
        this.rememberMeTimeout = rememberMeTimeout;
    }

    public Integer getGlobalSessionTimeout() {
        return globalSessionTimeout;
    }

    public void setGlobalSessionTimeout(Integer globalSessionTimeout) {
        this.globalSessionTimeout = globalSessionTimeout;
    }

    public Integer getSessionValidationInterval() {
        return sessionValidationInterval;
    }

    public void setSessionValidationInterval(Integer sessionValidationInterval) {
        this.sessionValidationInterval = sessionValidationInterval;
    }
}