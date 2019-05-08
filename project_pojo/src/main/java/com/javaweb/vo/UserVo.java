package com.javaweb.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 * @author Administrator
 *
 */
public class UserVo implements Serializable{

	private String id;//用户标识
	private String account;//用户账号
	private String user_name;//用户名称
	private String password;//密码
	private String nick_name;//用户昵称
	private String user_icon;//头像
	private Date last_login_time;//最后登录时间
	private Integer age;//年龄
	private Integer sex;//性别 1男  0女
	private Integer marry_flag;//婚否
	private Integer education;//学历
	private String phone;//手机号
	private String email;//邮箱
	private String prov;//省级
	private String city;//地市级
	private String dist;//区县
	private String address;//地址
	private String idcard;//身份证号
	private String remark;//备注
	private String salt;//盐
	private Integer status;//状态:0  已禁用 1 正在使用 2 已删除
	private String rememberMe;//记住密码

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getUser_icon() {
		return user_icon;
	}
	public void setUser_icon(String user_icon) {
		this.user_icon = user_icon;
	}

	public Date getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}

	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getMarry_flag() {
		return marry_flag;
	}
	public void setMarry_flag(Integer marry_flag) {
		this.marry_flag = marry_flag;
	}

	public Integer getEducation() {
		return education;
	}
	public void setEducation(Integer education) {
		this.education = education;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getDist() {
		return dist;
	}
	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}
}