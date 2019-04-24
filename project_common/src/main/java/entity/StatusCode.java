package entity;

/**
 * @program: project_parent
 * @description: 状态码
 * @author: YuKai Fan
 * @create: 2019-04-24 15:59
 **/
public enum StatusCode {
    OK(200),ERROR(201),LOGINERROR(202),ACCESSERROR(203),REMOTEERROR(204),REPERROR(205);
    private Integer value;

    private StatusCode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}