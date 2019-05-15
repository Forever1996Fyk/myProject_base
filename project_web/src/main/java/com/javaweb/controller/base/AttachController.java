package com.javaweb.controller.base;

import com.javaweb.constant.Constant;
import com.javaweb.entity.Result;
import com.javaweb.enums.ResultEnum;
import com.javaweb.enums.StatusEnum;
import com.javaweb.enums.UploadResultEnum;
import com.javaweb.pojo.Attachment;
import com.javaweb.properties.UploadProjectProperties;
import com.javaweb.service.common.AttachmentService;
import com.javaweb.util.FileUploadUtil;
import com.javaweb.util.IdWorker;
import com.javaweb.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @program: project_parent
 * @description: 文件controller
 * @author: YuKai Fan
 * @create: 2019-05-09 15:13
 **/
@RestController
@RequestMapping("/api")
public class AttachController {
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private HttpServletResponse response;


    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result uploadAttach(@RequestParam("fileUpload") MultipartFile multipartFile, Integer type) {
        String path = "";
        switch (type) {
            case 0: //上传图片
                path = "/pictrue";
                break;
            case 1: //上传文件
                path = "/fileUpload";
                break;
        }
        if (path.equals("")) {
            return new Result(false, UploadResultEnum.UPLOAD_IMG_FAIL.getValue(), "请传入文件类型1:文件,0:图片");
        }
        Attachment attachment = FileUploadUtil.getFile(multipartFile, path, type);
        try {
            return saveFile(multipartFile, attachment);
        } catch (IOException | NoSuchAlgorithmException e) {
            return new Result(false, UploadResultEnum.UPLOAD_FAIL.getValue(), UploadResultEnum.UPLOAD_FAIL.getMessage(), attachment);
        }
    }

    /**
     * 获取图片
     */
    @RequestMapping(value = "/getPicture", method = RequestMethod.GET)
    public void getPicture(String attachId) throws IOException {
        Attachment attachment = attachmentService.findOneById(attachId);

        if (attachment != null) {
            if (!(StringUtils.isEmpty(attachment.getAttach_path()) || attachment.getAttach_path().equals(Constant.DEFAULT_PATH_IMG))) {

                UploadProjectProperties properties = SpringContextUtil.getBean(UploadProjectProperties.class);
                String filePath = properties.getFilePath();
                String spPath = properties.getStaticPath().replace("*", "");
                String path = filePath + attachment.getAttach_path().replace(spPath, "");

                File file = new File(path);
                if (file.exists()) {
                    FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
                    return;
                }
            }
        }
        Resource resource = new ClassPathResource("static" + Constant.DEFAULT_PATH_IMG);
        FileCopyUtils.copy(resource.getInputStream(), response.getOutputStream());
    }

    /**
     * 保存文件
     * @param multipartFile
     * @param attachment
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private Result saveFile(MultipartFile multipartFile, Attachment attachment) throws IOException, NoSuchAlgorithmException {

//        Map<String, Object> map = new HashMap<>();
//        map.put("attach_sha1", FileUploadUtil.getFileSHA1(multipartFile));
        //根据sha1值，获取文件,判断文件是否存在
        Attachment attachmentOld = attachmentService.findBySha1(FileUploadUtil.getFileSHA1(multipartFile));

        if (attachmentOld != null) {
            FileUploadUtil.transferTo(multipartFile, attachmentOld);
            //将文件信息更新到数据库中
            attachmentService.update(attachmentOld);

            return new Result(true, ResultEnum.SUCCESS.getValue(), ResultEnum.SUCCESS.getMessage(), attachmentOld);
        }

        FileUploadUtil.transferTo(multipartFile, attachment);
        //将文件信息保存到数据库中
        attachment.setId(String.valueOf(idWorker.nextId()));
        attachment.setStatus(StatusEnum.Normal.getValue());
        attachmentService.add(attachment);

        return new Result(true, ResultEnum.SUCCESS.getValue(), ResultEnum.SUCCESS.getMessage(), attachment);
    }

    /**
     * 判断文件类型(图片,还是文件)
     * @param multipartFile
     * @return
     */
    public Integer fileType(MultipartFile multipartFile) {
        // 判断是否为图片格式
        String[] types = {
                "image/gif",
                "image/jpg",
                "image/jpeg",
                "image/png"
        };
        Integer type = 0;
        if (!FileUploadUtil.isContentType(multipartFile, types)) {
            type = 1;
        }
        return type;
    }

}