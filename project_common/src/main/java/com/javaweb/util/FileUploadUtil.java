package com.javaweb.util;

import com.javaweb.enums.UploadResultEnum;
import com.javaweb.exception.ResultException;
import com.javaweb.pojo.Attachment;
import com.javaweb.properties.UploadProjectProperties;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: project_parent
 * @description: 文件上传工具类
 * @author: YuKai Fan
 * @create: 2019-05-13 16:24
 **/
public class FileUploadUtil {

    /**
     * 创建一个Attachment实体对象
     * @param multipartFile
     * @param modulePath
     * @return
     */
    public static Attachment getFile(MultipartFile multipartFile, String modulePath, Integer fileType) throws Exception {
        if (multipartFile.getSize() == 0) {
            throw new ResultException(UploadResultEnum.NO_FILE_NULL.getValue(), UploadResultEnum.NO_FILE_NULL.getMessage());
        }
        Attachment attachment = new Attachment();
        attachment.setAttach_type(fileType);
        attachment.setAttach_size(multipartFile.getSize());
        attachment.setAttach_name(FileUploadUtil.genFileName(multipartFile.getOriginalFilename()));
        attachment.setAttach_path(getPathPattern() + modulePath + FileUploadUtil.genDateMkdir("yyyyMMdd") + attachment.getAttach_name());

        return attachment;
    }

    /**
     * 生成指定格式的目录名称(日期格式)
     * @param format
     * @return
     */
    private static String genDateMkdir(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return "/" + sdf.format(new Date()) + "/";
    }

    /**
     * 生成随机唯一的文件名
     * @param originalFileName
     * @return
     */
    public static String genFileName(String originalFileName) {
        String fileSuffix = ToolUtil.getFileSuffix(originalFileName);
        IdWorker idWorker = new IdWorker();

        return String.valueOf(idWorker.nextId()).replace("-", "") + fileSuffix;
    }

    /**
     * 获取文件上传目录的静态资源路径
     * @return
     */
    public static String getPathPattern() {
        UploadProjectProperties properties = SpringContextUtil.getBean(UploadProjectProperties.class);
        return properties.getStaticPath().replace("/**", "");
    }

}