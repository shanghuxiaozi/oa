package com.friends.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.friends.entity.MfMessagePush;
import com.friends.entity.MfUserInfo;
import com.friends.entity.MfUserSingleAuth;
import com.friends.entity.SysUser;
import com.friends.entity.vo.MfUserSingleAuthDynamicQueryVo;
import com.friends.service.MfMessagePushService;
import com.friends.service.MfUserInfoService;
import com.friends.service.MfUserSingleAuthService;
import com.friends.utils.Result;
import com.friends.utils.enums.SingleAuthEnum;
import com.friends.utils.enums.UserTypeEnum;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * 用户单身认证 controller <br>
 * 名称：UserSingleAuthController.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年10月26日 上午9:31:57 <br>
 * 
 * @since 2017年10月26日
 * @authour wenxun
 */
@Controller
@RequestMapping(value = "singleAuth")
public class UserSingleAuthController {

	private static Logger logger = LoggerFactory.getLogger(UserSingleAuthController.class);
	@Autowired
	private MfUserSingleAuthService mfUserSingleAuthService;
	@Autowired
	private MfUserInfoService sysUserinfoService;
	@Autowired
	private MfMessagePushService messagePushService;
	@Value("${friends.accessKey}")
	private String accessKey; // 上传凭证key
	@Value("${friends.secretKey}")
	private String secretKey; // 秘钥key
	@Value("${friends.bucketname}")
	private String bucketname; // 要上传的七牛云空间名称
	@Value("${friends.imgprefix}")
	private String imgprefix; // 七牛云图片路劲前缀

	@RequestMapping(value = "page")
	public ModelAndView toPage(ModelAndView model) {
		model.setViewName("user/authUserList");
		return model;
	}

	@RequestMapping(value = "index")
	@ResponseBody
	public String xxxxxx(HttpServletRequest request) {
		
		Principal userPrincipal = request.getUserPrincipal();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(principal.getClass());
		if (principal instanceof UserDetails) {
			System.out.println(((UserDetails) principal).getUsername());
		}
		if (principal instanceof Principal) {
			System.out.println(((Principal) principal).getName());
		}
		return "xxxxxxxxxx";
	}

	/**
	 * 进行单身认证审核 <br>
	 * 
	 * @param id
	 *            String 唯一标识 <br>
	 * @param personid
	 *            String 用户唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年10月26日 下午4:54:13 wenxun创建
	 */
	@RequestMapping(value = "singleAuth", method = RequestMethod.POST)
	@ResponseBody
	public Result singleAuth(String id, String authResult) {
		if (authResult.equals(SingleAuthEnum.AUDIT_PASSED.getCode())) {
			// 审核通过
			// 步骤1：将用户表状态改为 单身认证用户
			// 步骤2：将单身认证表 审核状态改为 审核通过
			// 步骤3：将用户表 真实姓名改为 认证表中的 真实姓名
			MfUserSingleAuth singleAuth = mfUserSingleAuthService.queryById(id);
			singleAuth.setAuthResult(SingleAuthEnum.AUDIT_PASSED.getCode());
			MfUserInfo userinfo = sysUserinfoService.queryById(singleAuth.getUserId());
			userinfo.setUserType(UserTypeEnum.SINGLE_USER.getCode());
			userinfo.setName(singleAuth.getRealName());
			// hibernate的事物机制，两条都会进行更改.
			sysUserinfoService.add(userinfo);
			// 向该用户推送消息
			MfMessagePush messagePush = new MfMessagePush();
			messagePush.setCreatTime(new Date());
			messagePush.setType("0");
			messagePush.setUserId(singleAuth.getUserId());
			messagePush.setMessageContent("您的单身认证已通过！");
			messagePush.setMessageType("1");
			messagePush.setIsNewuserTips("0");
			messagePushService.add(messagePush);
			return new Result(200, "处理成功");
		} else {
			// 审核未通过
			// 将单身认证表 状态改为 审核未通过就行了.
			MfUserSingleAuth singleAuth = mfUserSingleAuthService.queryById(id);
			singleAuth.setAuthResult(SingleAuthEnum.AUDIT_NOT_THROUGH.getCode());
			mfUserSingleAuthService.add(singleAuth);
			return new Result(200, "处理成功");
		}
	}

	// 上传图片
	@RequestMapping(value = "uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public Result uploadImg(MultipartFile muFile) throws IllegalStateException, IOException {
		logger.info("开始图片上传");
		// 获取真实文件名
		String filename = muFile.getOriginalFilename();
		// 1970年1月1号 00:00:00 到现在的总毫秒数。 作为图片名称，防重复
		long timeMillis = System.currentTimeMillis();
		String[] postfix = filename.split("\\.");
		String[] imgFormat = { "png", "jpg", "bmp", "jpeg" };
		boolean isTrue = false;
		for (int i = 0; i < imgFormat.length; i++) {
			if (postfix[1].equals(imgFormat[i])) {
				isTrue = true;
				break;
			}
		}
		if (false == isTrue) {
			return new Result(401, "上传的图片不符合规则！");
		}
		// 图片名称
		String result = timeMillis + "." + postfix[postfix.length - 1];
		// 图片地址
		// File uploadPic = new File("/temp/image/" + result);
		File uploadPic = new File("D:\\Myphoto\\" + result);
		// MultipartFile 转file
		muFile.transferTo(uploadPic);
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		// Windows和linux路劲区分;
		// String localFilePath = "/temp/image/" + result;
		String localFilePath = "D:\\Myphoto\\" + result;
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = result;
		// 图片上传凭证 及空间名称
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucketname);
		try {
			// 开始上传
			Response response = uploadManager.put(localFilePath, key, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			// 上传成功后的完整图片路劲
			String imgPath = imgprefix + putRet.key;
			return new Result(200, "上传成功", imgPath);
		} catch (QiniuException ex) {
			Response r = ex.response;
			logger.info("图片上传失败,错误信息如下:" + r.error);
			return new Result(400, "图片上传失败!");
		}
	}

	/**
	 * 保存单身认证信息
	 * 
	 * @param realName
	 * @param idcard
	 * @param imgPath
	 * @param request
	 * @param id
	 *            String 唯一标识
	 * @param personid
	 *            String 用户唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年10月26日 上午11:37:24 wenxun创建
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Result saveSingleAuth(String realName, String idcard, String imgPath, HttpServletRequest request) {
		logger.info("将单身认证信息保存到单身认证表!");
		String openId = (String) request.getSession().getAttribute("openId");
		// 根据openId 找到对应的用户 user
		MfUserInfo userinfo = sysUserinfoService.queryUserByOpenid(openId);
		// 保证每个用户在认证表里面都只有一条认证信息，多次认证只在原信息基础上进行修改
		MfUserSingleAuth userSingleAuth = mfUserSingleAuthService.queryByUserId(userinfo.getId());
		if (userSingleAuth == null) {
			userSingleAuth = new MfUserSingleAuth();
		}
		// !=null 代表不是第一次认证了。
		userSingleAuth.setAuthResult(SingleAuthEnum.PENDING_AUDIT.getCode());
		userSingleAuth.setCreationTime(new Date());
		userSingleAuth.setIdcard(idcard);
		userSingleAuth.setIdcardImg(imgPath);
		userSingleAuth.setRealName(realName);
		userSingleAuth.setUserId(userinfo.getId());
		MfUserSingleAuth uSingleAuth = mfUserSingleAuthService.add(userSingleAuth);
		if (uSingleAuth != null) {
			logger.info("保存成功");
			return new Result(200, "保存成功");
		}
		logger.info("保存失败");
		return new Result(400, "保存失败");
	}

	// 查询 包括动态结构查询 跟选择的约束方式有关
	// 建议不管有没有约束，都统一使用此查询方式进行查询
	// 范围约束如何传递参数？单独的参数 属性名称Min 属性名称Max 对于这个接口service和dao有对应关系
	@RequestMapping(value = "queryDynamic")
	@ResponseBody
	public Result queryDynamic(Integer page, Integer pageSize, String queryRestraintJson, HttpServletRequest request) {
		logger.info("查询");
		logger.info(queryRestraintJson);
		MfUserSingleAuthDynamicQueryVo userSingleAuthVo = JSON.parseObject(queryRestraintJson,
				MfUserSingleAuthDynamicQueryVo.class);
		userSingleAuthVo.setPage(page - 1);
		userSingleAuthVo.setPageSize(pageSize);
		// 获取当前用户
		SysUser user = (SysUser) request.getSession().getAttribute("sysUser");
		userSingleAuthVo.getT().setLabourUnionId(user.getLabourUnionId());
		Result mapResult = mfUserSingleAuthService.queryDynamic(userSingleAuthVo);
		return new Result(200, "查询成功", mapResult.getData(), mapResult.getTotal());
	}

}
