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
@Table(name="tb_attachment")
public class Attachment implements Serializable{

	@Id
	private String id;//附件标识
	private String owner_id;//所属标识
	private String owner_entity;//所属表
	private String attach_origin_title;//文件原名
	private String attach_utily;//附件属性，例身份证证明
	private Integer attach_type;//附件类型0图片1附件
	private String attach_name;//文件名
	private Long attach_size;//文件大小
	private String attach_postfix;//文件后缀
	private String attachment;//附件
	private String remark;//备注
	private Integer status;//状态:0  已禁用 1 正在使用
	private String create_user_id;//创建人
	private java.util.Date create_time;//创建时间
	private String update_user_id;//更新人
	private java.util.Date update_time;//更新时间

	
	public String getId() {		
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getOwner_id() {		
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getOwner_entity() {		
		return owner_entity;
	}
	public void setOwner_entity(String owner_entity) {
		this.owner_entity = owner_entity;
	}

	public String getAttach_origin_title() {		
		return attach_origin_title;
	}
	public void setAttach_origin_title(String attach_origin_title) {
		this.attach_origin_title = attach_origin_title;
	}

	public String getAttach_utily() {		
		return attach_utily;
	}
	public void setAttach_utily(String attach_utily) {
		this.attach_utily = attach_utily;
	}

	public Integer getAttach_type() {		
		return attach_type;
	}
	public void setAttach_type(Integer attach_type) {
		this.attach_type = attach_type;
	}

	public String getAttach_name() {		
		return attach_name;
	}
	public void setAttach_name(String attach_name) {
		this.attach_name = attach_name;
	}

	public Long getAttach_size() {		
		return attach_size;
	}
	public void setAttach_size(Long attach_size) {
		this.attach_size = attach_size;
	}

	public String getAttach_postfix() {		
		return attach_postfix;
	}
	public void setAttach_postfix(String attach_postfix) {
		this.attach_postfix = attach_postfix;
	}

	public String getAttachment() {		
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
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
