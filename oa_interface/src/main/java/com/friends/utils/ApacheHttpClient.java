package com.friends.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 使用Apache的http实现网络请求
 * 
 * @author pengyl
 */
public class ApacheHttpClient {

	/**
	 * post请求
	 * 
	 * @param url
	 *            请求路径
	 * @param param
	 *            请求参数
	 * @return
	 */
	public static String postHttp(String url, Map<String, String> param) {
		String result = "";
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<String> keys = param.keySet();
		for (String key : keys) {
			params.add(new BasicNameValuePair(key, param.get(key)));
		}
		try {
			httpost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse response = HttpClientBuilder.create().build().execute(httpost);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			System.out.println("网络异常");
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 *            请求路径
	 * @return
	 */
	public static String getHttp(String url) {
		String result = "";
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(httpGet);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			System.out.println("http请求异常");
		}

		return result;
	}
}
