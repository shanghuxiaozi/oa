package com.friends.controller;

import java.io.File;
import java.io.IOException;
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
import com.friends.entity.MfActivityReview;
import com.friends.entity.vo.MfActivityReviewDynamicQueryVo;
import com.friends.service.MfActivityReviewService;
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
 * 活动回顾 controller <br>
 * 名称：MfActivityReviewController.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年12月28日 上午10:06:30 <br>
 * @since  2017年12月28日
 * @authour wenxun
 */
@Controller
@RequestMapping(value = "activityReview")
public class MfActivityReviewController {
	private static Logger logger = LoggerFactory.getLogger(MfActivityReviewController.class);
	@Autowired
	MfActivityReviewService mfActivityReviewService;

	@Value("${friends.accessKey}")
	private String accessKey; // 上传凭证key
	@Value("${friends.secretKey}")
	private String secretKey; // 秘钥key
	@Value("${friends.bucketname}")
	private String bucketname; // 要上传的七牛云空间名称
	@Value("${friends.imgprefix}")
	private String imgprefix; // 七牛云图片路劲前缀


	@RequestMapping(value = "list")
	public ModelAndView page() {
		logger.info("进入活动回顾列表");
		return new ModelAndView("activityReview/activityreviewList");
	}

	// 单个新增
	@RequestMapping(value = "add")
	@ResponseBody
	public Result add(String json) {
		logger.info("json:"+json);
		MfActivityReview mfActivityReview = JSON.parseObject(json, MfActivityReview.class);
		mfActivityReview = mfActivityReviewService.add(mfActivityReview);
		return new Result(200, "新增成功");
	}

	// 单个更新
	@RequestMapping(value = "update")
	@ResponseBody
	public Result update(MfActivityReview mfActivityReview) {
		// 加入自定义参数校验，后期将直接支持JSR303
		mfActivityReviewService.update(mfActivityReview);
		return new Result(200, "更新成功");
	}

	// 单个删除
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(String id) {
		// 加入自定义参数校验，后期将直接支持JSR303
		mfActivityReviewService.delete(id);
		return new Result(200, "删除成功");
	}

	// 批量删除
	// 数组参数以json方式传递。
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public Result batchDelete(String mfActivityReviewArrayJson) {
		List<MfActivityReview> mfActivityReviews = JSON.parseArray(mfActivityReviewArrayJson, MfActivityReview.class);
		// 加入自定义参数校验，后期将直接支持JSR303
		mfActivityReviewService.batchDelete(mfActivityReviews);
		return new Result(200, "批量删除成功");
	}

	// 查询 包括动态结构查询 跟选择的约束方式有关
	// 建议不管有没有约束，都统一使用此查询方式进行查询
	// 范围约束如何传递参数？单独的参数 属性名称Min 属性名称Max 对于这个接口service和dao有对应关系
	@RequestMapping(value = "queryDynamic")
	@ResponseBody
	public Result queryDynamic(Integer page,Integer pageSize,String queryRestraintJson) {
		MfActivityReviewDynamicQueryVo mfActivityReviewDynamicQueryVo = JSON.parseObject(queryRestraintJson,
				MfActivityReviewDynamicQueryVo.class);
		mfActivityReviewDynamicQueryVo.setPage(page-1);
		mfActivityReviewDynamicQueryVo.setPageSize(pageSize);
		Result mapResult = mfActivityReviewService.queryDynamic(mfActivityReviewDynamicQueryVo);
		return new Result(200, "查询成功", mapResult.getData(), mapResult.getTotal());
	}
	
	// 上传图片
	@RequestMapping(value = "uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public Result uploadImg(MultipartFile file) throws IllegalStateException, IOException {
		logger.info("开始图片上传");
		// 获取真实文件名
		String filename = file.getOriginalFilename();
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
		Map<String, String> dataMap = new HashedMap();
		// 图片名称
		String result = timeMillis + "." + postfix[postfix.length - 1];
		// 图片地址
		// File uploadPic = new File("/temp/image/" + result);
		//File uploadPic = new File("D:\\Myphoto\\" + result);
		// MultipartFile 转file

		// Windows和linux路劲区分;
		String localFilePath = null;
		File uploadPic = null;
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			localFilePath = "D:\\Myphoto\\" + result;
			uploadPic = new File("D:\\Myphoto\\" + result);
		} else {
			localFilePath = "/temp/image/" + result;
			uploadPic = new File("/temp/image/" + result);
		}
		file.transferTo(uploadPic);
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);

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
			dataMap.put("src", imgPath);
			dataMap.put("title", result);
			
			// 此方法针对layui 返回格式必须保持一致.
			return new Result(0, "上传成功", dataMap);
		} catch (QiniuException ex) {
			Response r = ex.response;
			logger.info("图片上传失败,错误信息如下:" + r.error);
			return new Result(400, "图片上传失败!");
		}
	}

}
