package com.javaweb.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @program: project_parent
 * @description: 通用工具类
 * @author: YuKai Fan
 * @create: 2019-05-13 16:56
 **/
public class ToolUtil {

    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName) {
        if (!fileName.isEmpty()) {
            int lastIndexOf = fileName.lastIndexOf(".");
            return fileName.substring(lastIndexOf);
        }
        return "";
    }

    /**
     * 获取项目不同模式下的根路径
     * @return
     */
    public static String getProjectPath() {
        String filePath = ToolUtil.class.getResource("").getPath();
        String projectPath = ToolUtil.class.getClassLoader().getResource("").getPath();
        StringBuilder path = new StringBuilder();

        if (!filePath.startsWith("file:/")) {
            //开发模式下根路径
            char[] filePathArray = filePath.toCharArray();
            char[] projectPathArray = projectPath.toCharArray();
            for (int i = 0; i < filePathArray.length; i++) {
                if (projectPathArray.length > i && filePathArray[i] == projectPathArray[i]) {
                    path.append(filePathArray[i]);
                } else {
                    break;
                }
            }
        } else if (!projectPath.startsWith("file:/")) {
            //部署服务器模式下根路径
            projectPath = projectPath.replace("/WEB-INF/classes/", "");
            projectPath = projectPath.replace("/target/classes/", "");
            try {
                path.append(URLDecoder.decode(projectPath, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return projectPath;
            }
        } else {
            //jar包启动模式下根路径
            String property = System.getProperty("java.class.path");
            int firstIndex = property.lastIndexOf(System.getProperty("path.separator")) + 1;
            int lastIndex = property.lastIndexOf(File.separator) + 1;
            path.append(property, firstIndex, lastIndex);
        }

        File file = new File(path.toString());
        String rootPath = "/";
        try {
            rootPath = URLDecoder.decode(file.getAbsolutePath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rootPath.replaceAll("\\\\", "/");
    }
}