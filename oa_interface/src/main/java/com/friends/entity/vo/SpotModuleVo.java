package com.friends.entity.vo;
/**
 * 
 * @author 宋琪
 *
 */
public class SpotModuleVo {
	//模板id
	private String moduleId;
	
	//是否有新的消息
	private Boolean isNew = false;
	
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
}
