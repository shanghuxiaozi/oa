package com.friends.controller;

import com.alibaba.fastjson.JSON;
import com.friends.entity.MfUserDataDetails;
import com.friends.entity.vo.MfUserDataDetailsDynamicQueryVo;
import com.friends.entity.vo.MfUserInfoDynamicQueryVo;
import com.friends.service.MfUserDataDetailsService;
import com.friends.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户图像管理
 * @author wenxun
 * @Date: 2018/4/16 13:49
 * @Description：
 */
@Controller
@RequestMapping("/userPhoto")
public class UserPhotoController {

	private static Logger logger = LoggerFactory.getLogger(UserPhotoController.class);

	@Autowired
	MfUserDataDetailsService mfUserDataDetailsService;

	@RequestMapping("/toPage")
	public ModelAndView toPage(ModelAndView model){
		logger.info("进入头像列表页面");
		model.setViewName("user/userPhotoList");
		return model;
	}

	@RequestMapping(value = "queryDynamic")
	@ResponseBody
	public Result queryDynamic(Integer page, Integer pageSize, String queryRestraintJson) {
		MfUserDataDetailsDynamicQueryVo userDataDetailsDynamicQueryVo = JSON.parseObject(queryRestraintJson, MfUserDataDetailsDynamicQueryVo.class);
		userDataDetailsDynamicQueryVo.setPage(page - 1);
		userDataDetailsDynamicQueryVo.setPageSize(pageSize);
		Result mapResult = mfUserDataDetailsService.queryDynamic(userDataDetailsDynamicQueryVo);
		return new Result(200, "查询成功", mapResult.getData(), mapResult.getTotal());
	}

	/**
	 * 功能描述: 根据用户ID修改用户头像
	 * @param: [id, headImg]
	 * @return: com.friends.utils.Result
	 * @auther: wenxun
	 * @date: 2018/4/16 15:39
	 */
	@RequestMapping(value = "/updateImg",method = RequestMethod.POST)
	@ResponseBody
	public Result updateImg(String id,String headImg){
		MfUserDataDetails userDataDetails = mfUserDataDetailsService.queryById(id);
		if(null != userDataDetails){
			userDataDetails.setHeadImg(headImg);
			mfUserDataDetailsService.update(userDataDetails);
			return new Result(200,"修改成功");
		}
		return new Result(400,"修改失败，数据已经不存在");
	}

}
