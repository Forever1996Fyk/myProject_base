package com.javaweb.enums;

/**
 * @program: project_parent
 * @description: 后台返回结果集枚举
 * @author: YuKai Fan
 * @create: 2019-05-13 16:45
 **/
public enum UploadResultEnum {
    /**
     * 文件操作
     */
    NO_FILE_NULL(401, "文件不能为空"),
    NO_FILE_TYPE(402, "不支持该文件类型"),
    UPLOAD_FAIL(403, "上传失败"),
    UPLOAD_IMG_FAIL(404, "上传图片失败"),
    UPLOAD_FILE_FAIL(405, "上传文件失败"),

    ;

    private Integer value;

    private String message;

    UploadResultEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}