package com.javaweb.enums;

/**
 * @program: project_parent
 * @description: 数据状态
 * @author: YuKai Fan
 * @create: 2019-04-25 15:49
 **/
public enum StatusEnum {
    Delete(2),Normal(1),Disable(0);
    public Integer value;

    StatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}