package com.friends.controller;

import com.friends.entity.MfLikeTable;
import com.friends.entity.MfMemberStyle;
import com.friends.entity.MfMessagePush;
import com.friends.service.MfLikeTableService;
import com.friends.service.MfMessagePushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.friends.entity.vo.MfMemberStyleDynamicQueryVo;
import com.friends.service.MfMemberStyleService;
import com.friends.utils.Result;

import java.util.Date;
import java.util.List;

/**
 * 会员风采 
 * 名称：MemberStyleController.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2018年1月5日 下午4:55:56 <br>
 * @since  2018年1月5日
 * @authour wenxun
 */
@Controller
@RequestMapping(value = "memberStyle")
public class MemberStyleController {
	
	private static Logger logger = LoggerFactory.getLogger(MemberStyleController.class);
	
	@Autowired
	private MfMemberStyleService mfMemberStyleService;
	@Autowired
	private MfMessagePushService mfMessagePushService;
	@Autowired
	private MfLikeTableService mfLikeTableService;
	
	/**
	 * 进入会员风采列表页面
	 * 
	 * @return 列表页面
	 */
	@RequestMapping(value = "memberStyleList", method = RequestMethod.GET)
	public ModelAndView page() {
		logger.info("进入会员风采列表页面");
		return new ModelAndView("memberStyle/memberStyleList");
	}
	
	
	/**
	 * 根据模板相关条件queryRestraintJson查询模板数据
	 * 
	 * @param queryRestraintJson
	 * @return 模板列表
	 */
	@RequestMapping(value = "queryJoinTableDynamic", method = RequestMethod.POST)
	@ResponseBody
	public Result queryJoinTableDynamic(Integer page, Integer pageSize, String queryRestraintJson) {
		MfMemberStyleDynamicQueryVo memberStyleDynamicQueryVo = JSON.parseObject(queryRestraintJson,
				MfMemberStyleDynamicQueryVo.class);
		memberStyleDynamicQueryVo.setPage(page - 1);
		memberStyleDynamicQueryVo.setPageSize(pageSize);
		Result result = mfMemberStyleService.queryJoinTableDynamic(memberStyleDynamicQueryVo);
		return new Result(200, "查询成功", result.getData(), result.getTotal());
	}

	/*
	*  审核通过
	**/
	@RequestMapping(value = "pass", method = RequestMethod.POST)
	@ResponseBody
	public Result pass(String id){
		MfMemberStyle mfMemberStyle = mfMemberStyleService.queryById(id);
		if(null != mfMemberStyle){
			mfMemberStyle.setStatus("2");
			mfMemberStyleService.update(mfMemberStyle);
			return new Result(200,"审核通过");
		}
		return new Result(400,"审核失败，数据不存在。");
	}

	/*
	*  审核不通过
	**/
	@RequestMapping(value = "notThrough", method = RequestMethod.POST)
	@ResponseBody
	public Result notThrough(String id){
		MfMemberStyle mfMemberStyle = mfMemberStyleService.queryById(id);
		if(null != mfMemberStyle){
			mfMemberStyle.setStatus("3");
			mfMemberStyleService.update(mfMemberStyle);
			return new Result(200,"审核成功");
		}
		return new Result(400,"审核失败，数据不存在。");
	}

	/*
	* 删除数据
	*/
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Result delete(String id,String delContent){
		MfMemberStyle mfMemberStyle = mfMemberStyleService.queryById(id);
		if(mfMemberStyle != null){
			//删除发布的动态
			mfMemberStyleService.delete(id);
			//删除点赞记录
			MfLikeTable mfLikeTable = new MfLikeTable();
			mfLikeTable.setMemberStyleId(id);
			List<MfLikeTable> likeTableList = mfLikeTableService.queryByExample(mfLikeTable);
			if(null != likeTableList && likeTableList.size() > 0){
				mfLikeTableService.batchDelete(likeTableList);
			}
			//删除后，把删除理由发送给发表人
			MfMessagePush mfMessagePush = new MfMessagePush();
			mfMessagePush.setCreatTime(new Date());
			mfMessagePush.setMessageContent(delContent);
			mfMessagePush.setMessageType("1");
			mfMessagePush.setType("0");
			mfMessagePush.setUserId(mfMemberStyle.getUserId());
			mfMessagePush.setIsNewuserTips("0");
			mfMessagePushService.add(mfMessagePush);
			return new Result(200,"删除成功");
		}
		return new Result(400,"删除失败，数据不存在。");
	}
	
	
}
