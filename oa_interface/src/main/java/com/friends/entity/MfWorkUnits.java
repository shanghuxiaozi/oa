package com.friends.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//交友_工作单位表
@Entity
@Table(name = "mf_work_units")
public class MfWorkUnits {
	// 主键
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 工作单位
	private String workUnits;
	// 类型 1服务中心工会、各街道总工会、各工委会 2社区工联会、各企业工会  3各工会具体名称
	private String type;
	// 父ID
	private String pId;
	// 创建时间
	private Date creationTime;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setWorkUnits(String workUnits) {
		this.workUnits = workUnits;
	}

	public String getWorkUnits() {
		return workUnits;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}
	
}