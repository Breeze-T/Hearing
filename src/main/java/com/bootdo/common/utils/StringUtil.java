package com.bootdo.common.utils;

import org.springframework.util.StringUtils;

/**
 * 字符串为空判断
 * @author zz
 *
 */
public class StringUtil extends StringUtils{

	/**
	 * 判断空
	 */
	public static boolean isEmpty(Object obj){
		if(obj instanceof String){
			if(!StringUtils.isEmpty(obj)){
				obj = ((String)obj).trim();
				return StringUtils.isEmpty(obj);
			}
		}
		return StringUtils.isEmpty(obj);
	}

	/**
	 * 判断非空
	 */
	public static boolean isNotEmpty(Object obj){
		return !isEmpty(obj);
	}
}
