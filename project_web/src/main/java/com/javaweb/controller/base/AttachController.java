package com.javaweb.controller.base;

import com.javaweb.entity.Result;
import com.javaweb.enums.ResultEnum;
import com.javaweb.enums.StatusEnum;
import com.javaweb.enums.UploadResultEnum;
import com.javaweb.pojo.Attachment;
import com.javaweb.util.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @program: project_parent
 * @description: 文件controller
 * @author: YuKai Fan
 * @create: 2019-05-09 15:13
 **/
@Controller
@RequestMapping("/api")
public class AttachController {



    @RequestMapping("/upload/{type}")
    public Result uploadAttach(@RequestParam("fileUpload") MultipartFile multipartFile, @PathVariable Integer type) throws Exception {
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
            throw new Exception();
        }
        Attachment attachment = FileUploadUtil.getFile(multipartFile, path, type);
        try {
            return saveFile(multipartFile, attachment);
        } catch (IOException | NoSuchAlgorithmException e) {
            return new Result(false, UploadResultEnum.UPLOAD_IMG_FAIL.getValue(), UploadResultEnum.UPLOAD_IMG_FAIL..getMessage());
        }
    }

    private Result saveFile(MultipartFile multipartFile, Attachment attachment) {
        return new Result(true, ResultEnum.SUCCESS.getValue(), ResultEnum.SUCCESS.getMessage());
    }


}