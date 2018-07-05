package com.friends.utils;

/**
 * 描述
 *
 * @author wenxun
 * @version 1.0
 * @since 2018/1/20 16:35
 */
public class QiNiuMedia {

	private static QiNiuMedia media = null;
	public static final String ACCESSKEY = "ZVdKzPmyd_aOz6UpTcIIL0Odkl4Dv8cpw-Rg4yGR";
	public static final String SECRETKEY = "vgfIXnMK4i4EUlOTef4Ob2Tb8ng9GQHWAJcMCE5a";
	public static final String BUCKETNAME = "baoangonghui";
	public static final String DOMAIN = "p33szlc9l.bkt.clouddn.com";

	private String accessKey;// 设置accessKey
	private String secretKey;// 设置secretKey
	private String bucketName;// 设置存储空间
	private String domain;// 设置七牛域名

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * 实例化一个QiNiuMedia实例
	 * @uesr "xinzhifu@knet.cn"
	 * @date 2016年11月19日下午2:58:27
	 */
	public static synchronized QiNiuMedia getInstance() {
		if (media == null) {
			media = new QiNiuMedia();
			media.setAccessKey(ACCESSKEY);
			media.setSecretKey(SECRETKEY);
			media.setBucketName(BUCKETNAME);
			media.setDomain(DOMAIN);
		}
		return media;
	}
}
