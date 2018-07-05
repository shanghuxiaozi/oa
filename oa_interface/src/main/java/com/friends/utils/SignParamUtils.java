package com.friends.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignParamUtils {
	 
	/** 
     *  把requset中获取的参数进行转换
     * @param param 从request中获取的参数
     * @return 
     */
    public static Map<String, String> convertParams(Map<String, String[]> param) {
        Map<String, String> result = new HashMap<String, String>();
        if (param == null || param.size() <= 0) {
            return result;
        }
        for (String key : param.keySet()) {
            String value = param.get(key).length > 0 ? param.get(key)[0] : "";
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
    
    /** 
     * 除去数组中的空值和签名参数
     * @param param 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
	public static Map<String, String> paraFilter(Map<String, String> param) {
        Map<String, String> result = new HashMap<String, String>();
        if (param == null || param.size() <= 0) {
            return result;
        }
        for (String key : param.keySet()) {
            String value = param.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")){
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
	
	/** 
     * 拼接签名字符串
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createSignString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    /**
     * 将参数进行排序并拼接为一个字符串
     * 拼接时自动忽略sign
     * @param param
     * @return
     */
    public static String getSignParam(Map<String, String[]> param){
    	String result = "";
    	Map<String, String> map = convertParams(param);
    	result = createSignString(map);
    	
    	return result;
    }

}
