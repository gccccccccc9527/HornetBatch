package com.hornet.core.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <li>功能描述：jdbc连接数据库
 * <li>作者：sunguanchun
 * <li>最后更新时间：2018年5月9日 上午10:49:10
 */
public class MysqlJDBCHelper {
	
    //db.properties读取
    private static String db_config_path = "db.properties";
    private static Logger logger = LoggerFactory.getLogger(MysqlJDBCHelper.class);
    
    /**
     * 获取Connection
     * @return
     */
    public static Connection getConnection(){
        Connection connection = getConnection(db_config_path);
        return connection;
    }
    
    /**
     * 通过properties文件名获取Connection
     * @param fileName properties文件名
     * @return Connection
     */
    public static Connection getConnection(String fileName) {
        //IO流读取db.properties文件
        InputStream in = MysqlJDBCHelper.class.getClassLoader().getResourceAsStream(fileName);
        //读取参数
        Properties p = new Properties();
        try {
            p.load(in);
        } catch (IOException e) {
        	logger.error("读取db.properies出错！", e);
            e.printStackTrace();
        }
        String url = p.getProperty("mysql.url");
        String user = p.getProperty("mysql.username");
        String password = p.getProperty("mysql.password");
        String driver=p.getProperty("mysql.driver");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
        	logger.error("加载数据库驱动出错！", e);
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
        	logger.error("获取Connection出错！", e);
            e.printStackTrace();
        }
        //关闭流
        try {
            if (in!=null) {
                in.close();
            }
        } catch (IOException e) {
        	logger.error("关闭读取db.properies的流出错！", e);
            e.printStackTrace();
        }
        return connection;
    }
    
    /**
     * 关闭资源
     * @param con
     * @param st
     * @param rs
     */
    public static void close(Connection con, PreparedStatement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            	logger.error("关闭ResultSet出错！", e);
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
            	logger.error("关闭PreparedStatement出错！", e);
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
            	logger.error("关闭Connection出错！", e);
            }
        }
    }
    
}