package com.javaweb.constant;

/**
 * @program: project_parent
 * @description: 数据状态
 * @author: YuKai Fan
 * @create: 2019-04-25 15:49
 **/
public enum  StatusConstant {
    Delete(2),Normal(1),Disable(0);
    public Integer value;

    StatusConstant(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}