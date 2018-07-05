package com.friends.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.friends.entity.Pay;
import com.friends.entity.vo.DailyDynamicQueryVo;
import com.friends.service.TaskService;
import com.friends.utils.Result;

@Controller
@RequestMapping(value = "task")
public class TaskController {
	private static Logger logger = LoggerFactory.getLogger(PayController.class);
	
	@Autowired
	TaskService taskService;
	
	@RequestMapping(value = "dailyInden")
	public ModelAndView page() {
		return new ModelAndView("task/taskIndex");
	}

	@RequestMapping(value = "queryDynamic")
	@ResponseBody
	public Result queryDynamic(Integer page, Integer pageSize, String queryRestraintJson) {
		DailyDynamicQueryVo mfInvitationUserDataDynamicQueryVo = JSON.parseObject(queryRestraintJson,
				DailyDynamicQueryVo.class);
		mfInvitationUserDataDynamicQueryVo.setPage(page - 1);
		mfInvitationUserDataDynamicQueryVo.setPageSize(pageSize);
		Result mapResult = taskService.queryDynamic(mfInvitationUserDataDynamicQueryVo);
		List<Pay> list = (List<Pay>)mapResult.getData();
		return new Result(200, "查询成功", list, mapResult.getTotal());
	}
	
}
