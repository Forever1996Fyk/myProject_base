package com.javaweb.pojo;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_user")
@SQLDelete(sql = "update tb_user set status = 0 where id = ?")//软删除
public class User implements Serializable{

	@Id
	private String id;//用户标识


	
	private String account;//用户账号
	private String user_name;//用户名称
	private String password;//密码
	private String nick_name;//用户昵称
	private String user_icon;//头像
	private java.util.Date last_login_time;//最后登录时间
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
	private Integer status;//状态:0  已禁用 1 正在使用 2 已删除
	private String create_user_id;//创建人
	private Date create_time;//创建时间
	private String update_user_id;//更新人
	private Date update_time;//更新时间

	
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

	public java.util.Date getLast_login_time() {		
		return last_login_time;
	}
	public void setLast_login_time(java.util.Date last_login_time) {
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

	public String getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}

	public java.util.Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(String update_user_id) {
		this.update_user_id = update_user_id;
	}

	public java.util.Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(java.util.Date update_time) {
		this.update_time = update_time;
	}


	
}
