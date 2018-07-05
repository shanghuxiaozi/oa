package com.friends.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 角色权限信息
 * @author lzl
 * 2017-8-22
 */
@Entity
@Table(name = "sys_role_permission")
public class SysRolePermission implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;//编号
	@Column(name = "role_id")
	private String roleId;//角色id
	@Column(name = "permission_id")
	private String permissionId;//权限id

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionId() {
		return permissionId;
	}
}
