package com.hornet.core.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <li>功能描述：所有跑批任务需要继承此类
 * <li>作者：sunguanchun
 * <li>创建时间：2018年5月30日
 * <li>补充：
 */
public class AbstactTaskMain {

	private static Logger logger = LoggerFactory.getLogger(AbstactTaskMain.class);
	
	/**批量开始时间戳*/
	private long startTime;
	
	/**
	 * 批量调起开始日志
	 * @param taskName
	 * @return
	 */
	protected long taskBegin(String taskName) {
		startTime = System.currentTimeMillis();
		logger.info("=====================================================================");
		logger.info(">> 【{}】批量开始...", taskName);
		logger.info(">> 开始时间：{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		logger.info("=====================================================================");
		return startTime;
	}
	
	/**
	 * 批量结束日志
	 * @param taskName
	 */
	protected void taskEnd(String taskName) {
		long end = System.currentTimeMillis();
		logger.info("=====================================================================");
		logger.info(">> 【{}】批量结束！", taskName);
		logger.info(">> 结束时间：{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		logger.info(">> 总耗时：{}秒", String.valueOf((end-startTime)/1000));
		logger.info("=====================================================================\n\n\n");
		return;
		
	}
	
	/**
	 * 批量结束日志，有异常
	 * @param taskName
	 */
	protected void taskEndByErr(String taskName, String error) {
		long end = System.currentTimeMillis();
		logger.info("=====================================================================");
		logger.info(">> 【{}】批量结束！", taskName);
		logger.info(">> 结束时间：{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		logger.info(">> 总耗时：{}秒", String.valueOf((end-startTime)/1000));
		logger.info(">> 异常：{}", error==null?"none":error);
		logger.info("=====================================================================\n\n\n");
		return;
		
	}

	
	//getters and setters
	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
}
