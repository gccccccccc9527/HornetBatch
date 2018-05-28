package com.hornet.modules.demo;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hornet.modules.demo.util.DemoConfigUtil;

/**
 * <li>功能描述：demo 批量
 * <li>作者：sunguanchun
 * <li>最后更新时间：2018年5月8日 上午10:54:42
 */
public class DemoTaskMain {

	private static Logger logger = LoggerFactory.getLogger(DemoTaskMain.class);
	
	/**
	 * 程序入口
	 * @param args
	 */
	public static void main(String[] args) {
		DemoTaskMain demoMain = new DemoTaskMain();
		demoMain.step1DoSth();
	}
	
	/**
	 * 步骤一
	 */
	private void step1DoSth(){
		logger.info("开始执行demo批量步骤一...执行时间={}", new Date().toLocaleString());
		String test = DemoConfigUtil.getValue("test");
		logger.info("读取配置文件demo-config.txt中的test={}", test);
		logger.info("执行demo批量步骤一结束！执行时间={}", new Date().toLocaleString());
	}
	
	
	
}
