package com.hornet.core.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <li>功能描述：jdbc操作数据库
 * <li>作者：sunguanchun
 * <li>最后更新时间：2018年5月9日 上午11:04:52
 */
public class MysqlJDBCSqlTemplate {

	private static Logger logger = LoggerFactory.getLogger(MysqlJDBCSqlTemplate.class);

	/**
	 * 填充条件参数
	 * 
	 * @param st
	 * @param objs
	 */
	private static void setParams(PreparedStatement st, Map<Integer, Object> params) {
		// 当前第几位参数
		int flag = 0;
		try {
			Set<Entry<Integer, Object>> entrySet = params.entrySet();
			for (Entry<Integer, Object> entry : entrySet) {
				Integer key = entry.getKey();
				Object value = entry.getValue();

				flag = key;
				// 获得参数的类型
				String paramType = value.getClass().getName();
				if (Integer.class.getName().equals(paramType)) {
					// 判断是否是int类型
					st.setInt(key, (int) value);
				} else if (Double.class.getName().equals(paramType)) {
					// 判断是否是dobule类型
					st.setDouble(key, (double) value);
				} else if (String.class.getName().equals(paramType)) {
					// 判断是否是string类型
					st.setString(key, (String) value);
				} else {
					// 其他
					st.setObject(key, value);
				}
			}
		} catch (SQLException e) {
			logger.error("注入第{}的值时失败！", flag, e);
		}
	}

	/**
	 * 查询
	 * 
	 * @param sql
	 *            要执行的sql语句
	 * @param params
	 *            执行sql语句需要的参数
	 * @return 取出数据库的数据, key是字段名或字段别名(小写字母), value应对字段的值
	 */
	public static Map<String, Object> selectOne(String sql, Map<Integer, Object> params) {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Map<String, Object> results = null;

		logger.info("执行SQL：{}", sql);
		logger.info("输入参数：{}", params.toString());
		try {
			con = MysqlJDBCHelper.getConnection();
			// sql执行准备工具
			st = con.prepareStatement(sql);
			setParams(st, params);
		} catch (SQLException e) {
			logger.error("执行工具PreparedStatement准备出错！", e);
		}

		try {
			// 执行sql取到返回数据白结果集
			rs = st.executeQuery();
		} catch (SQLException e) {
			logger.error("执行sql取返回数据失败！", e);
		}

		try {
			// 元数据，对象取取到的结果集数据的描述
			ResultSetMetaData rsmd = rs.getMetaData();
			int cloumCount = rsmd.getColumnCount();
			// 判断结果集是否还有数据 (数据是一条记录的方式取出)
			if (rs.next()) {
				results = new HashMap<String, Object>();
				for (int i = 1; i <= cloumCount; i++) {
					// rsmd.getColumnName(i);//表的字段名或字段别名
					// rs.getObject(i);//取到字段对应的值
					results.put(rsmd.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			logger.error("结果集转为Map失败！", e);
		} finally {
			OracleJDBCHelper.close(con, st, rs);
		}
		logger.info("返回结果：{}", results);
		return results;
	}

	/**
	 * 查询
	 * 
	 * @param sql
	 *            要执行的sql语句
	 * @param objs
	 *            执行sql语句需要的参数
	 * @return 取出数据库的数据，每一条记录是一个map，key是字段名或字段别名(小写字母), value应对字段的值
	 */
	public static List<Map<String, Object>> selectList(String sql, Map<Integer, Object> params) {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Map<String, Object>> results = null;

		logger.info("执行SQL：{}", sql);
		logger.info("输入参数：{}", params.toString());
		try {
			con = MysqlJDBCHelper.getConnection();
			// sql执行准备工具
			st = con.prepareStatement(sql);
			setParams(st, params);
		} catch (SQLException e) {
			logger.error("执行工具PreparedStatement准备出错！", e);
		}

		try {
			// 执行sql取到返回数据白结果集
			rs = st.executeQuery();
		} catch (SQLException e) {
			logger.error("执行sql取返回数据失败！", e);
		}

		try {
			// 元数据，对象取取到的结果集数据的描述
			ResultSetMetaData rsmd = rs.getMetaData();
			int cloumCount = rsmd.getColumnCount();
			results = new ArrayList<Map<String, Object>>();
			// 判断结果集是否还有数据 (数据是一条记录的方式取出)
			while (rs.next()) {
				Map<String, Object> record = new HashMap<String, Object>();
				for (int i = 1; i <= cloumCount; i++) {
					// rsmd.getColumnName(i);//表的字段名或字段别名
					// rs.getObject(i);//取到字段对应的值
					record.put(rsmd.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
				results.add(record);
			}
		} catch (SQLException e) {
			logger.error("结果集转为List失败！", e);
		} finally {
			OracleJDBCHelper.close(con, st, rs);
		}
		logger.info("返回结果：{}", results);
		return results;
	}

	/**
	 * 执行sql，针对insert,delete,update
	 * 
	 * @param sql
	 * @param params
	 * @return 返回结果是受影响行数
	 */
	public static int execSql(String sql, Map<Integer, Object> params) {
		Connection con = null;
		PreparedStatement st = null;
		int result = 0;

		logger.info("执行SQL：{}", sql);
		logger.info("输入参数：{}", params.toString());
		try {
			con = MysqlJDBCHelper.getConnection();
			// sql执行准备工具
			st = con.prepareStatement(sql);
			setParams(st, params);
		} catch (SQLException e) {
			logger.error("执行工具PreparedStatement准备出错！", e);
		}

		try {
			/*
			 * 执行sql，针对insert,delete,update 返回结果是受影响行数
			 */
			result = st.executeUpdate(); // 执行sql , 针对insert, delete,
			logger.info("操作数据成功，受影响的数据条数为：{}条", result);
		} catch (SQLException e) {
			logger.error("执行sql失败失败！", e);
		} finally {
			OracleJDBCHelper.close(con, st, null);
		}
		return result;
	}

	/**
	 * 批量执行sql，针对insert,update
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int[] execSqlBatch(String sql, List<Map<Integer, Object>> params) {
		Connection con = null;
		PreparedStatement st = null;
		int[] result = null;

		logger.info("批量执行SQL：{}", sql);
		logger.info("输入参数：{}", params.toString());
		try {
			con = MysqlJDBCHelper.getConnection();
			// sql执行准备工具
			st = con.prepareStatement(sql);
			for (Map<Integer, Object> objs : params) {
				setParams(st, objs);
				st.addBatch();
			}
		} catch (SQLException e) {
			logger.error("批量执行工具PreparedStatement准备出错！", e);
		}

		try {
			/*
			 * 批量执行sql，针对insert,delete,update 返回结果是受影响行数
			 */
			result = st.executeBatch(); // 执行sql , 针对insert, delete,
			logger.info("批量操作数据成功，受影响的数据条数为：{}条", result);
		} catch (SQLException e) {
			logger.error("批量执行sql失败失败！", e);
		} finally {
			OracleJDBCHelper.close(con, st, null);
		}
		return result;
	}

	public static void main(String[] args) {
		// 插入1条
		// Map<Integer, Object> params = new HashMap<>();
		// params.put(1, 99);
		// params.put(2, "史泰龙");
		// String sql1 = "insert into sgc_test values (?,?)";
		// int res = JDBCSqlTemplate.execSql(sql1, params);
		// System.out.println(res);
		// 批量插入
		// String sql2 = "insert into sgc_test values (?,?)";
		// List<Map<Integer, Object>> params = new ArrayList<>();
		// for (int i = 0; i < 5; i++) {
		// Map<Integer, Object> param = new HashMap<>();
		// param.put(1, i);s
		// param.put(2, "批量第"+i+"条数据");
		// params.add(param);
		// }
		// int[] res = JDBCSqlTemplate.execSqlBatch(sql2, params);
		// System.out.println(Arrays.toString(res));
		// 查一条
		// Map<Integer, Object> params = new HashMap<>();
		// params.put(1, 99);
		// String sql1 = "select st.* from sgc_test st where 1=1 and st.id = ?";
		// Map<String, Object> res = JDBCSqlTemplate.selectOne(sql1, params);
		// System.out.println(res);
		// 查多条
		// Map<Integer, Object> params = new HashMap<>();
		// params.put(1, 1);
		// String sql1 = "select st.* from sgc_test st where 1=1 and st.id = ?";
		// List<Map<String,Object>> res = JDBCSqlTemplate.selectList(sql1, params);
		// System.out.println(res);
		// 更新
		// Map<Integer, Object> params = new HashMap<>();
		// params.put(1, "孙冠春");
		// params.put(2, 0);
		// String sql1 = "update sgc_test st set st.name = ? where st.id = ?";
		// int res = JDBCSqlTemplate.execSql(sql1, params);
		// System.out.println(res);
		// 删除
		// Map<Integer, Object> params = new HashMap<>();
		// params.put(1, "孙冠春");
		// params.put(2, 0);
		// String sql1 = "delete from sgc_test st where 1=1 and st.name = ? and st.id =
		// ?";
		// int res = JDBCSqlTemplate.execSql(sql1, params);
		// System.out.println(res);
	}

}
