package com.xswq.consumer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {
	//判断手机格式是否正确
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		String expression = "^[1][1-9][0-9]{9}$";
		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}

		return isValid;

	}
	//判断邮箱格式是否正确
	public static boolean isEmailStringValid(String email){

		if (null==email || "".equals(email)) return false;

		//Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配

		Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配

		Matcher m = p.matcher(email);

		return m.matches();

	}
	//密码正则表达式（除了特殊字符）
	public static boolean isPasswordNO(String password) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	//验证汉字区间
	public static boolean isWordsSection(String password) {
		Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5]{1,9}$");
		Matcher m = p.matcher(password);
		return m.matches();
	}

}
