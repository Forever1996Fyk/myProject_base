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
@Table(name="tb_permission")
@SQLDelete(sql = "update tb_permission set status = 2 where id = ?")//软删除
public class Permission implements Serializable{

	@Id
	private String id;//权限标识
	private String name;//权限名称
	private String pid;//上级权限id
	private Integer sort;//排名 注意mysql版本中的关键字与保留字，原来的range不能使用
	private Integer level;//等级
	private String remark;//备注
	private String url;//链接
	private Integer status;//状态:0  已禁用 1 正在使用
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

	public String getName() {		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {		
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
