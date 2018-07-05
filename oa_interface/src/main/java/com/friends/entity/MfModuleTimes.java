package com.friends.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

//模块的更新次数
@Entity
@Table(name = "mf_moduletimes")
public class MfModuleTimes {
	// id主键
		@Id
		@GeneratedValue(generator = "uuid")
		@GenericGenerator(name = "uuid", strategy = "uuid")
		private String id;
		// 用户Id
		private String userId;
		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		//模块id,1平台介绍
		private String moduleId;
		
		//浏览次数
		private Integer timesOfBrowsing;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getModuleId() {
			return moduleId;
		}

		public void setModuleId(String moduleId) {
			this.moduleId = moduleId;
		}

		public Integer getTimesOfBrowsing() {
			return timesOfBrowsing;
		}

		public void setTimesOfBrowsing(Integer timesOfBrowsing) {
			this.timesOfBrowsing = timesOfBrowsing;
		}
		
}
