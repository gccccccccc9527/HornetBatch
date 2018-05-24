package com.hornet.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <li>功能描述：获取src/config.properties中的配置信息
 * <li>作者：sunguanchun
 * <li>创建时间：2017年10月27日 下午5:24:54
 */
public final class ConfigUtil{
	
	/**properties对象*/
	private static Properties pro = new Properties();
	
	/**properties文件名称*/
	private static String FILE_NAME = "config.properties";
	
	/**文件路径*/
	private static String FILE_PATH = "src/resources/";
	
	/**
	  * 类的私有构造函数 
	  */
	private ConfigUtil(){};
	
	/**
	  * 获取System.properties中的信息
	  */
	static{
		InputStream in = ConfigUtil.class.getResourceAsStream("/"+FILE_NAME);
		try {
			if(null != in){
				pro.load(in);
			}else{
				in = ClassLoader.getSystemResourceAsStream(FILE_PATH+FILE_NAME);
				if(null != in){
					pro.load(in);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	  *	同构key获取对应值
	  */
	public static String getValue(String key){
		return pro.getProperty(key);
	}
	
	/**
	 * 自测
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(ConfigUtil.getValue("demo_config_path"));
		
	}
}
