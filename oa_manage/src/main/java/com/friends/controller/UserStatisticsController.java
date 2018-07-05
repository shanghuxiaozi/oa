package com.friends.controller;

import com.friends.entity.MfUserInfo;
import com.friends.service.MfInvitationUserDataService;
import com.friends.service.MfUserDataDetailsService;
import com.friends.service.MfUserInfoService;
import com.friends.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户统计
 *
 * @author wenxun
 * @version 1.0
 * @since 2018/1/31 14:32
 */
@Controller
@RequestMapping(value = "userStatistics")
public class UserStatisticsController {

	private static Logger logger = LoggerFactory.getLogger(UserStatisticsController.class);

	@Autowired
	MfUserInfoService mfUserInfoService;
	@Autowired
	MfInvitationUserDataService mfInvitationUserDataService;
	@Autowired
	MfUserDataDetailsService mfUserDataDetailsService;

	@RequestMapping(value = "page")
	public ModelAndView page() {
		logger.info("进入用户统计页面。");
		return new ModelAndView("user/userStatistics");
	}

	@RequestMapping(value = "toPage")
	public ModelAndView toPage() {
		logger.info("进入用户统计页面。");
		return new ModelAndView("user/userStatistics2");
	}

	/**
	 * 功能描述:根据时间段 统计用户资料
	 * @param: [startDate, endDate]
	 * @return: com.friends.utils.Result
	 * @auther: wenxun
	 * @date: 2018/4/12 16:20
	 */
	@RequestMapping(value = "statistics")
	@ResponseBody
	public Result statistics(String startDate,String endDate){
		//统计注册用户
		Result result = mfUserDataDetailsService.statisticsByTime(startDate,endDate);
		List<Map<String, Object>> listMap = (List<Map<String, Object>>)result.getData();
		if(null != listMap && listMap.size() > 0){
			return new Result(200,"查询成功",listMap);
		}
		return new Result(400,"查无数据");
	}

	/**
	 * 功能描述: 统计全部受邀用户
	 * @param: [userType, startDate, endDate]
	 * @return: com.friends.utils.Result
	 * @auther: wenxun
	 * @date: 2018/4/12 16:20
	 */
	@RequestMapping(value = "userStatistics")
	@ResponseBody
	public Result userStatistics(String userType,String startDate,String endDate){
		//统计用户
		//年龄段分布  18-24  25-29  30-34  35-39  40-60
		//岗位职业分布   机关单位  事业单位  企业  其他
		//已注册未注册分布比例
		//男女比例分布
		Result result = mfInvitationUserDataService.statisticsData();
		List<Map<String, Object>> listMap = ( List<Map<String, Object>>)result.getData();
		return new Result(200,"查询成功",listMap);
	}



}
