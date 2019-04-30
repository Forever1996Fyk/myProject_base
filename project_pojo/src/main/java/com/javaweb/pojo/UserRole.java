package com.javaweb.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_user_role")
public class UserRole implements Serializable{

	@Id
	private Integer id;//主键标识


	
	private String user_id;//用户标识
	private String role_id;//角色标识
	private String create_user_id;//创建人
	private java.util.Date create_time;//创建时间
	private String update_user_id;//更新人
	private java.util.Date update_time;//更新时间

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser_id() {		
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getRole_id() {		
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
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
