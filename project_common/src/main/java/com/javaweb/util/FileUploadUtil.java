package com.javaweb.util;

import com.javaweb.enums.UploadResultEnum;
import com.javaweb.exception.ResultException;
import com.javaweb.pojo.Attachment;
import com.javaweb.properties.UploadProjectProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    public static Attachment getFile(MultipartFile multipartFile, String modulePath, Integer fileType) {
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

    /**
     * 获取文件的SHA1值
     * @param multipartFile
     * @return
     */
    public static String getFileSHA1(MultipartFile multipartFile) {
        if (multipartFile.getSize() == 0) {
            throw new ResultException(UploadResultEnum.NO_FILE_NULL.getValue(), UploadResultEnum.NO_FILE_NULL.getMessage());
        }

        byte[] buffer = new byte[4096];
        try (InputStream fis = multipartFile.getInputStream()) {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                sha1.update(buffer, 0 , len);
            }
            BigInteger SHA1Bi = new BigInteger(1, sha1.digest());
            return SHA1Bi.toString(16);
        } catch (IOException | NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * 保存文件以及获取文件MD5值和SHA1值
     * @param multipartFile
     * @param attachment
     */
    public static void transferTo(MultipartFile multipartFile, Attachment attachment) throws NoSuchAlgorithmException, IOException {

        byte[] buffer = new byte[4096];
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        try (OutputStream fos = Files.newOutputStream(getDestFile(attachment).toPath()); InputStream fis = multipartFile.getInputStream()){
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                md5.update(buffer, 0, len);
                sha1.update(buffer, 0 ,len);
            }
            fos.flush();
        }
        BigInteger MD5Bi = new BigInteger(1, md5.digest());
        BigInteger SHA1Bi = new BigInteger(1, sha1.digest());
        attachment.setAttach_md5(MD5Bi.toString(16));
        attachment.setAttach_sha1(SHA1Bi.toString(16));
    }

    /**
     * 获取目标文件对象
     * @param attachment
     * @return
     * @throws IOException
     */
    public static File getDestFile(Attachment attachment) throws IOException {

        //创建保存文件对象
        String path = attachment.getAttach_path().replace(getPathPattern(), "");
        String filePath = getUploadPath() + path;
        File file = new File(filePath.replace("//", "/"));
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        return file;

    }

    /**
     * 获取文件上传保存路径
     * @return
     */
    private static String getUploadPath() {
        UploadProjectProperties properties = SpringContextUtil.getBean(UploadProjectProperties.class);
        return properties.getFilePath();
    }

    /**
     * 判断文件是否为支持的格式
     * @param multipartFile
     * @param types
     * @return
     */
    public static boolean isContentType(MultipartFile multipartFile, String[] types) {
        List<String> typeList = Arrays.asList(types);
        return typeList.contains(multipartFile.getContentType());
    }
}