package com.friends.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friends.dao.MfSpotPushDao;
import com.friends.entity.MfModuleTimes;
import com.friends.entity.MfSpotPush;
import com.friends.entity.vo.SpotModuleVo;
import com.friends.service.MfModuleTimesService;
import com.friends.service.MfSpotPushService;
import com.friends.utils.SpotAndHotConstant;

/**
 * 
 * @author 宋琪
 *
 */
@Service
public class MfSpotPushServiceImpl extends BaseService<MfSpotPushDao, MfSpotPush> implements MfSpotPushService {
	private static Logger logger = LoggerFactory.getLogger(MfSpotPushServiceImpl.class);
	
	@Autowired
	MfSpotPushService mfSpotPushService;
	
	@Autowired
	MfModuleTimesService mfModuleTimesService;
	
	@Override
	public List<SpotModuleVo> checkSpotAndModule(MfSpotPush t, MfModuleTimes m) {
		//首先查询个人从模块1开始查起,2表示活动回顾，3表示交友课堂，5表示交友活动，6表示我的好友，7表示交友互动，9表示平台留言后的系统消息，10表示站内私信，11公开信
		List<SpotModuleVo> list = new ArrayList<SpotModuleVo>();
		for( int i = 1; i<= SpotAndHotConstant.TOTAL;i++){
			String moduleId = i+"";
			SpotModuleVo spotVo = new SpotModuleVo();
			spotVo.setModuleId(moduleId);
			t.setModuleId(moduleId);
			m.setModuleId(moduleId);
			List<MfSpotPush> spotList = mfSpotPushService.queryByExample(t);
			MfSpotPush spot = null;
			//查询不存在,赋值
			if( null == spotList || spotList.size() <1 ){
				spot = new MfSpotPush();
				spot.setTimesOfBrowsing(0);
			}else{
				spot = spotList.get(0);
			}
			
			if(4==i||6==i||7==i||9==i||10==i){
				m.setUserId(t.getUserId());
			}else{
				m.setUserId(null);
			}
			
			List<MfModuleTimes> moduleList = mfModuleTimesService.queryByExample(m);
			
			if(null == moduleList || moduleList.size() < 1 ){
				spotVo.setIsNew(false);
			}else{
				MfModuleTimes module = moduleList.get(0);
				spotVo.setIsNew(module.getTimesOfBrowsing().intValue() != spot.getTimesOfBrowsing().intValue());
			}
			
			list.add(spotVo);
			
		}
		
		
		return list;
		
		
	}

	/**
	 * 凭moduleId查询
	 * */
	@Override
	public SpotModuleVo checkSpotAndModuleByModuleId(MfSpotPush t, MfModuleTimes m , String moduleId) {
		//首先查询个人从模块1开始查起
			SpotModuleVo spotVo = new SpotModuleVo();
			spotVo.setModuleId(moduleId);
			t.setModuleId(moduleId);
			m.setModuleId(moduleId);
			List<MfSpotPush> spotList = mfSpotPushService.queryByExample(t);
			MfSpotPush spot = null;
			//查询不存在,赋值
			if( null == spotList || spotList.size() <1 ){
				spot = new MfSpotPush();
				spot.setTimesOfBrowsing(0);
			}else{
				spot = spotList.get(0);
			}
			
			if("4".equals(moduleId)||"6".equals(moduleId)||"7".equals(moduleId)||"9".equals(moduleId)||"10".equals(moduleId)){
				m.setUserId(t.getUserId());
			}else{
				m.setUserId(null);
			}
			
			List<MfModuleTimes> moduleList = mfModuleTimesService.queryByExample(m);
			
			if(null == moduleList || moduleList.size() < 1 ){
				spotVo.setIsNew(false);
			}else{
				MfModuleTimes module = moduleList.get(0);
				spotVo.setIsNew(module.getTimesOfBrowsing().intValue() != spot.getTimesOfBrowsing().intValue());
			}
			
			
		
		
		return spotVo;
		
		
	}

}
