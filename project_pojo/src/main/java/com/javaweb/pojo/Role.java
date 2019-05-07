package com.javaweb.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_role")
@SQLDelete(sql = "update tb_role set status = 2 where id = ?")//软删除
@Proxy(lazy = false)
public class Role implements Serializable{

	@Id
	private String id;//角色标识
	private String role_name;//角色名称
	private String remark;//备注
	private Integer status;//状态:0  已禁用 1 正在使用
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_role_permission",
			joinColumns = @JoinColumn(name = "role_id"),
			inverseJoinColumns = @JoinColumn(name = "permission_id")) //JoinTable注解主要是针对多对多关系的表
	private Set<Permission> permissions = new HashSet<>(0);//权限资源列表
	@ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JsonIgnore
	private Set<User> users = new HashSet<>(0);
	@Transient
	private Integer selected;//判断是否被选
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

	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
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

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Role role = (Role) o;

		if (!id.equals(role.id)) return false;
		if (!role_name.equals(role.role_name)) return false;
		return status.equals(role.status);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + role_name.hashCode();
		result = 31 * result + status.hashCode();
		return result;
	}
}
