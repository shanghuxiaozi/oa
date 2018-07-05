package com.friends.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.friends.entity.MfMessagePush;
import com.friends.entity.MfActivityEnroll;
import com.friends.entity.MfActivityIntegral;
import com.friends.entity.MfActivityRelease;
import com.friends.entity.MfIntegralDetails;
import com.friends.entity.MfUserImage;
import com.friends.entity.vo.MfActivityReleaseDynamicQueryVo;
import com.friends.service.MfMessagePushService;
import com.friends.service.MfActivityEnrollService;
import com.friends.service.MfActivityIntegralService;
import com.friends.service.MfActivityReleaseService;
import com.friends.service.MfIntegralDetailsService;
import com.friends.service.MfUserImageService;
import com.friends.service.MfUserInfoService;
import com.friends.utils.Result;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * 交友活动 控制器 <br>
 * 名称：FriendsActivityController.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年11月7日 下午7:55:23 <br>
 * 
 * @since 2017年11月7日
 * @authour wenxun
 */
@Controller
@RequestMapping(value = "friendsActivity")
public class FriendsActivityController {

	private static Logger logger = LoggerFactory.getLogger(ActivityController.class);
	
	@Autowired
	MfActivityReleaseService mfActivityReleaseService;
	@Autowired
	MfActivityEnrollService mfActivityEnrollService;
	@Autowired
	MfUserInfoService mfUserInfoService;
	@Autowired
	MfIntegralDetailsService mfIntegralDetailsService;
	@Autowired
	MfActivityIntegralService mfActivityIntegralService;
	@Autowired
	MfUserImageService mfUserImageService;
	@Autowired
	MfMessagePushService messagePushService;
	
	// 七牛云上传配置文件
	@Value("${friends.accessKey}")
	private String accessKey; // 上传凭证key
	@Value("${friends.secretKey}")
	private String secretKey; // 秘钥key
	@Value("${friends.bucketname}")
	private String bucketname; // 要上传的七牛云空间名称
	@Value("${friends.imgprefix}")
	private String imgprefix; // 七牛云图片路劲前缀

	/**
	 * 进入模板列表页面
	 * 
	 * @return 模板列表页面
	 */
	@RequestMapping(value = "activityList", method = RequestMethod.GET)
	public ModelAndView page() {
		return new ModelAndView("friendsActivity/activityList");
	}

	/**
	 * 进入活动报名页面
	 * 
	 * @return 模板列表页面
	 */
	@RequestMapping(value = "activityEnroll", method = RequestMethod.GET)
	public ModelAndView enroll() {
		return new ModelAndView("friendsActivity/activityEnroll");
	}

	/**
	 * 进入交友图片页面
	 * 
	 * @return 模板列表页面
	 */
	@RequestMapping(value = "activityImages", method = RequestMethod.GET)
	public ModelAndView activityImages() {
		return new ModelAndView("friendsActivity/activityImages");
	}

	/**
	 * 根据用户Id 查询参加过的活动详情
	 * 
	 * @param id
	 * @param id
	 *            String 唯一标识
	 * @param personid
	 *            String 用户唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年10月19日 上午11:59:00 wenxun创建
	 */
	@RequestMapping(value = "queryActivityByUserId", method = RequestMethod.POST)
	@ResponseBody
	public Result queryActivityByUserId(String id) {
		// 先查询该用户 有没有参加过活动
		int row = mfIntegralDetailsService.queryCountByUserId(id);
		if (row > 0) {
			// 根据用户id查询出积分详情(参加过的活动详情)!
			Result result = mfIntegralDetailsService.queryIntegralDetailsByUserId(id);
			if (result.getData() != null) {
				return new Result(200, "查询成功!", result.getData());
			}
		}
		return new Result(400, "该用户还没有参加过活动!");
	}

	/**
	 * 根据模板相关条件queryRestraintJson查询模板数据
	 * 
	 * @param queryRestraintJson
	 * @return 模板列表
	 */
	@RequestMapping(value = "queryDynamic", method = RequestMethod.POST)
	@ResponseBody
	public Result queryDynamic(String queryRestraintJson) {
		MfActivityReleaseDynamicQueryVo activityDynamicQueryVo = JSON.parseObject(queryRestraintJson,
				MfActivityReleaseDynamicQueryVo.class);
		// 活动类型 0:交友活动 1:普通活动
		activityDynamicQueryVo.getT().setType("0");
		Result result = mfActivityReleaseService.queryDynamic(activityDynamicQueryVo);
		return new Result(200, "查询成功", result.getData(), result.getTotal());
	}

	/**
	 * 初始进入模板添加页面
	 * 
	 * @return 添加页面
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(ModelAndView model) {
		model.setViewName("friendsActivity/activityAdd");
		return model;
	}

	/**
	 * 根据模板activity数据添加模板信息
	 * 
	 * @param activity
	 * @return Result
	 */

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(String json) {
		logger.info("add");
		MfActivityRelease activity = JSON.parseObject(json, MfActivityRelease.class);
		// 加入自定义参数校验，后期将直接支持JSR303
		activity.setActivityTerm("0");
		activity.setTime(new Date());
		activity.setCreateTime(new Date());
		activity.setUpdateTime(new Date());
		activity.setIsDelete(0);
		activity.setType("0");
		activity = mfActivityReleaseService.add(activity);
		// 图片上传返回相应信息
		return new Result(200, "新增成功");
	}

	
	/**
	 * 添加页面，首页图片上传
	 * 
	 * 
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public Object upload(MultipartFile file) throws IOException {
		// 获取文件名
		String filename = file.getOriginalFilename();
		long timeMillis = System.currentTimeMillis();
		String[] postfix = filename.split("\\.");
		String result = timeMillis + "." + postfix[postfix.length - 1];		
		// 图片存放地址
		File uploadPic;
		String localFilePath = null;
		// 判断操作系统 linux 和 windows
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
		    uploadPic = new File("D:\\Myphoto\\" + result);
			localFilePath = "D:\\Myphoto\\" + result;
		} else {
			uploadPic = new File("/temp/image/" + result);
			localFilePath = "/temp/image/" + result;
		}		
		file.transferTo(uploadPic);
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = result;
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucketname);
		try {
			Response response = uploadManager.put(localFilePath, key, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				// ignore
			}
		}
		MfActivityRelease sysActivity = new MfActivityRelease();
		sysActivity.setActivityImage(imgprefix + result);
		return sysActivity;
	}

	/**
	 * 活动内容，图片上传
	 * 
	 * 
	 */

	@RequestMapping(value = "upload2", method = RequestMethod.POST)
	@ResponseBody
	public Result upload2(MultipartFile file) throws IOException {
		Map<String, String> dataMap = new HashedMap();
		// 获取文件名
		String filename = file.getOriginalFilename();
		long timeMillis = System.currentTimeMillis();
		String[] postfix = filename.split("\\.");
		// 图片地址
		String result = timeMillis + "." + postfix[postfix.length - 1];		
		// 图片存放地址
		File uploadPic;
		String localFilePath = null;
		// 判断操作系统 linux 和 windows
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
		uploadPic = new File("D:\\Myphoto\\" + result);
		localFilePath = "D:\\Myphoto\\" + result;
		} else {
		uploadPic = new File("/temp/image/" + result);
		localFilePath = "/temp/image/" + result;
		}		
		file.transferTo(uploadPic);
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = result;
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucketname);
		try {
			Response response = uploadManager.put(localFilePath, key, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				// ignore
			}
		}
		String result2 = "";
		String webname = imgprefix + result;
		if (result != null && result != "") {
			dataMap.put("src", webname);
			dataMap.put("title", result);
			return new Result(0, "上传成功！", dataMap);
		} else {
			result2 = "{\"code\": 1 ,\"msg\": \"上传失败\" ,\"data\": {\"src\": \"\",\"title\": \"上传失败\"}}";
		}
		return new Result(0, "上传失败！");
	}

	/**
	 * 初始进入模板修改页面
	 * 
	 * @param id
	 *            模板id
	 * @return 进入页面
	 */

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(ModelAndView model, String id) {
		MfActivityRelease activity = mfActivityReleaseService.queryById(id);
		model.addObject("activity", activity);
		model.setViewName("friendsActivity/activityEdit");
		return model;
	}

	/**
	 * 根据模板activity数据修改模板信息
	 * 
	 * @param activity
	 *            模板信息
	 * @return Result
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(String json) {
		logger.info("update");
		MfActivityRelease activity = JSON.parseObject(json, MfActivityRelease.class);
		// 加入自定义参数校验，后期将直接支持JSR303
		activity.setUpdateTime(new Date());
		mfActivityReleaseService.updateSelective(activity);
		return new Result(200, "修改成功");
	}

	/**
	 * 根据模板activity数据删除模板
	 * 
	 * @param ids
	 *            模板id集合
	 * @return Result
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Result delete(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			MfActivityRelease temp = new MfActivityRelease();
			temp.setIsDelete(1);
			temp.setId(ids[i]);
			mfActivityReleaseService.updateSelective(temp);
		}
		return new Result(200, "删除成功");
	}

	/**
	 * 根据模板id获取模板详情
	 * 
	 * @param model
	 *            ModelAndView
	 * @param id
	 *            模板id
	 * @return 模板详情页面
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ModelAndView detail(ModelAndView model, String id) {
		MfActivityRelease activity = mfActivityReleaseService.queryById(id);
		model.addObject("activity", activity);
		model.addObject("detail", "detail");// 和修改是同一个页面，这个detail用来控制详情数据展示,不能编辑
		model.setViewName("friendsActivity/activityDetail");
		return model;
	}

	/**
	 * 初始进入报名详情页面
	 * 
	 * @return 报名页面
	 */
	@RequestMapping(value = "enrollDetail", method = RequestMethod.GET)
	public ModelAndView detail2(ModelAndView model, String id) {
		model.addObject("id", id);
		model.setViewName("friendsActivity/enrollDetail");
		return model;
	}

	/**
	 * 报名详情页面
	 * 
	 * @param queryRestraintJson
	 * @return 模板列表
	 */
	@RequestMapping(value = "ByActivityIdqueryUser", method = RequestMethod.POST)
	@ResponseBody
	public Result queryDynamic2(String id) {
		Result result = mfUserInfoService.queryByActivityId(id);
		if (result.getData() != null) {
			List<List<Map<String, Object>>> listMapData = (List<List<Map<String, Object>>>) result.getData();
			ArrayList<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
			for (List<Map<String, Object>> list : listMapData) {
				if (list != null && list.size() > 0) {
					arrayList.add(list.get(0));
				}
			}
			// logger.info(JSON.toJSONString("转换后的数据:"+arrayList));
			return new Result(200, "查询成功", arrayList);
		}
		return new Result(400, "该活动暂时没人参加");
	}

	/**
	 * 报名详情：签到：添加积分 改变状态
	 * 
	 * @param ids
	 *            模板id集合
	 * @return Result
	 */
	@RequestMapping(value = "addPoints", method = RequestMethod.POST)
	@ResponseBody
	public Result addPoints(String activityId, String userId) {
		// 添加积分
		MfActivityIntegral integral = mfActivityIntegralService.findIntegralByuId(userId);
		int add = integral.getIntegral() + 5;
		integral.setIntegral(add);
		// 生成一条积分详情
		MfIntegralDetails integralDetails = new MfIntegralDetails();
		Date date = new Date();
		int gainIntegral = 5;
		integralDetails.setActivityId(activityId);
		integralDetails.setCreationTime(date);
		integralDetails.setUserId(activityId);
		integralDetails.setGainIntegral(gainIntegral);
		mfIntegralDetailsService.add(integralDetails);
		// 改变状态
		MfActivityEnroll activityEnroll = mfActivityEnrollService.findActivityEnrollByUidAndAid(activityId, userId);
		activityEnroll.setType("1");
		mfActivityEnrollService.add(activityEnroll);
		return new Result(200, "签到成功");
	}

	/**
	 * 报名详情：未到：减少积分 改变状态
	 * 
	 * @param ids
	 *            模板id集合
	 * @return Result
	 */
	@RequestMapping(value = "minusPoints", method = RequestMethod.POST)
	@ResponseBody
	public Result minusPoints(String activityId, String userId) {
		// 减少积分
		MfActivityIntegral integral = mfActivityIntegralService.findIntegralByuId(userId);
		int add = integral.getIntegral() - 5;
		int minus = integral.getPoint() - 2;
		integral.setPoint(minus);
		integral.setIntegral(add);
		mfActivityIntegralService.add(integral);
		// 生成一条积分详情
		MfIntegralDetails integralDetails = new MfIntegralDetails();
		Date date = new Date();
		int gainIntegral = -5;
		integralDetails.setActivityId(activityId);
		integralDetails.setCreationTime(date);
		integralDetails.setUserId(activityId);
		integralDetails.setGainIntegral(gainIntegral);
		mfIntegralDetailsService.add(integralDetails);
		// 改变状态
		MfActivityEnroll activityEnroll = mfActivityEnrollService.findActivityEnrollByUidAndAid(activityId, userId);
		activityEnroll.setType("2");
		mfActivityEnrollService.add(activityEnroll);
		return new Result(200, "扣分成功");
	}

	/**
	 * 轮播图片上传
	 * 
	 * 
	 */
	@RequestMapping(value = "saveActivityImg", method = RequestMethod.POST)
	@ResponseBody
	public Result saveActivityImg(String photo_one, String photo_two, String photo_three, String photo_five) {
		String photo[] = { photo_one, photo_two, photo_three, photo_five };
		if (photo[0] != null && photo[0] != "" && photo[1] != null && photo[1] != "" && photo[2] != null
				&& photo[2] != "" && photo[3] != null && photo[3] != "") {
			for (int i = 0; i < photo.length; i++) {
				MfUserImage userImage = new MfUserImage();
				userImage.setUserId("abcdefghijklmnopqrstuvwxyz");
				userImage.setCreateTime(new Date());
				userImage.setImageName(photo[i]);
				userImage.setImageType("3");
				userImage.setIsUse("1");
				mfUserImageService.add(userImage);
			}
			return new Result(200, "上传成功");
		} else {
			return new Result(201, "请上传四张轮播图！");
		}
	}

	/**
	 * 发布活动成功，给所有用户推送消息
	 * 
	 * @param activity
	 * @return Result
	 */
	@RequestMapping(value = "pushMessage", method = RequestMethod.POST)
	@ResponseBody
	public Result pushMessage(String json) {
		MfActivityRelease activity = JSON.parseObject(json, MfActivityRelease.class);
		String title = activity.getTitle();
		List<String> userId = mfUserInfoService.queryUserId();
		if (userId != null && userId.size() > 0) {
			for (int i = 0; i < userId.size(); i++) {
				// 所有用户id
				String uId = userId.get(i);
				MfMessagePush messagePush = new MfMessagePush();
				messagePush.setCreatTime(new Date());
				messagePush.setType("0");
				messagePush.setUserId(uId);
				messagePush.setMessageType("2");
				messagePush.setMessageContent(title + "活动，报名即将开始！");
				messagePushService.add(messagePush);
			}
			return new Result(200, "新增成功");
		}

		return new Result(201, "新增失败");
	}

}
