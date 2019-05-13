package com.javaweb.properties;

import com.javaweb.util.ToolUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: project_parent
 * @description: 文件上传配置
 * @author: YuKai Fan
 * @create: 2019-05-13 17:07
 **/
@Component
@ConfigurationProperties(prefix = "project.upload")
public class UploadProjectProperties {

    //上传文件路径
    private String filePath;
    //上传文件静态访问路径
    private String staticPath = "/upload/**";

    public String getFilePath() {
        if (filePath == null) {
            return ToolUtil.getProjectPath() + "/upload/";
        }
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getStaticPath() {
        return staticPath;
    }

    public void setStaticPath(String staticPath) {
        this.staticPath = staticPath;
    }
}