package com.friends.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * 图片工具类
 * 
 * @author pengyl
 */
public class ImageUtil {

	private static Logger log = Logger.getLogger(ImageUtil.class);

	/**
	 * 根据orderId生成二维码图片 地址调用百度API生成短地址
	 * 
	 * @param orderId
	 * @return
	 */
	public static String createQRCodeByBaiduAPI(String orderId, String appId) {
		String ipUrl = GetProperties.getPropertiesValue("wx.qrUrl");
		String http = ipUrl + "/wxWap/busOrder";// 扫描时的连接地址
		http += "?orderId=" + orderId;
		// 为了获取到扫码者的openId需到微信平台进行信息校验，地址信息返回结果中由一个code参数，对该code进行解码即可获取到扫码者的openId
		String httpUrl = SysConfigUtil.OAuth2_url;
		httpUrl = httpUrl.replace("APPID", appId).replace("REDIRECT_URI", http);
		/* 将二维码地址转换为短地址，减小二维码密集度 */
		httpUrl = shortUrlByBaiduAPI(httpUrl);
		if (StringUtils.isBlank(httpUrl)) {
			return null;
		}
		return createQRCode(httpUrl);
	}

	/**
	 * 根据orderId生成二维码图片
	 * 
	 * @param orderId
	 * @param appId
	 * @param url
	 * @return
	 */
	public static String createQRCode(String url) {
		String format = "png";
		String path = GetProperties.getPropertiesValue("path");
		String background = path + "/Image/background.png";
		String logo = path + "/Image/logo.png";
		if (StringUtils.isBlank(path)) {
			return null;
		}
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowDateTime = dateFormat.format(date);
		// 二维码名称
		String QrCodeName = "transfer_" + nowDateTime + "_" + UUID.randomUUID().toString().replace("-", "_") + ".png";
		File outputFile = new File(path + QrCodeName);
		// 生成二维码图片
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			MatrixUtil.writeToFile(url, background, logo, format, outputFile);
			return path + QrCodeName;
		} catch (Exception e) {
			log.info("----生成二维码异常：", e);
			return null;
		}
	}

	/**
	 * 生成短地址
	 * 
	 * @param longUrl
	 * @return
	 */
	public static String shortUrlByBaiduAPI(String longUrl) {
		if (longUrl == null || longUrl.length() == 0) {
			return null;
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("url", longUrl);
		String responseStr = ApacheHttpClient.postHttp("http://www.dwz.cn/create.php", param);
		if (StringUtils.isBlank(responseStr)) {
			log.info("-----长地址转为短地址异常！");
			return null;
		}
		Map<String, Object> map = JSONObject.parseObject(responseStr);
		if (map == null || map.get("status") == null) {
			log.info("-----长地址转为短地址异常！");
			return null;
		}
		if (map.get("status").toString().equals("0") && map.get("tinyurl") != null) {
			return map.get("tinyurl").toString();
		} else {
			log.info("-----长地址转为短地址失败！");
			return null;
		}
	}

	/**
	 * 将文件转换为byte[]
	 * 
	 * @param filePath
	 * @return
	 */
	public static byte[] fileToByteArray(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return null;
		}
		try {
			InputStream is = new FileInputStream(new File(filePath));
			int size = is.available();
			byte[] data = null;
			if (size > 0) {
				data = new byte[is.available()];
				is.read(data);
			}
			is.close();
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return true;
		}
		File file = new File(filePath);
		if (!file.exists()) {// 文件不存
			return true;
		}
		return file.delete();
	}
}
