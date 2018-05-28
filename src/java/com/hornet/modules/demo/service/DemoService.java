package com.hornet.modules.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hornet.core.db.MysqlJDBCSqlTemplate;

/**
 * <li>功能描述：测试连接mysql数据库
 * <li>作者：sunguanchun
 * <li>创建时间：2018年5月28日
 * <li>补充：
 */
public class DemoService {

	/**
	 * 查h_user表全部
	 * @return
	 */
	public static List<Map<String,Object>> selectAll(){
		Map<Integer, Object> params = new HashMap<>();
//		params.put(1, "1");
		String sql = "select hu.* from h_user hu where 1=1";
		List<Map<String,Object>> res = MysqlJDBCSqlTemplate.selectList(sql, params);
		return res;
	}
	
	/**
	 * 自测
	 * @param args
	 */
	public static void main(String[] args) {
		List<Map<String,Object>> res = DemoService.selectAll();
		System.out.println(res);
	}
	
}
