package com.friends.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.friends.entity.SysUser;

public class PasswordHelper {
	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private String algorithmName = "md5";
	private int hashIterations = 2;

	public void encryptPassword(SysUser user) {
		// String salt=randomNumberGenerator.nextBytes().toHex();
		String newPassword = new SimpleHash(algorithmName, user.getPassword(),
				ByteSource.Util.bytes(user.getUserName()), hashIterations).toHex();
		// String newPassword = new SimpleHash(algorithmName,
		// user.getPassword()).toHex();
		user.setPassword(newPassword);
		user.setSalt("");

	}
/*
	public static void main(String[] args) {
		String str1 = "be0debf79f63e1f67232e6978ef51d0b";
		String str2 = "be0debf79f63e1f67232e6978ef51d0b";
		if(str1.equals(str2)){
			PasswordHelper passwordHelper = new PasswordHelper();
			SysUser user = new SysUser();
			user.setUserName("sys_admin");
			// 
			user.setPassword(str1);
			passwordHelper.encryptPassword(user);
			System.out.println(user);
			System.out.println(user.getUserName());
			System.out.println(user.getPassword());
		}
	}*/

}
