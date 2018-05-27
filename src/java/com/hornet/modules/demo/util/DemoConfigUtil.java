package com.hornet.modules.demo.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hornet.core.util.ConfigUtil;

/**
 * <li>功能描述：demo 模块配置文件操作
 * <li>作者：sunguanchun
 * <li>最后更新时间：2018年5月17日 下午5:50:42
 */
public class DemoConfigUtil {

	private static Logger logger = LoggerFactory.getLogger(DemoConfigUtil.class);
	/**配置文件名*/
	private static String FILE_NAME = "demo-config.txt";
	/**配置文件路径*/
	private static String FILE_PATH = ConfigUtil.getValue("demo_config_path");
	
	/**
	 * 私有构造
	 */
	private DemoConfigUtil(){}
	
	/**
	 * 加载配置文件，读取key=value存入map
	 * @return
	 */
	private static Map<String, String> read(){
		InputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		Map<String, String> confs = null;
		try {
			in = new FileInputStream(FILE_PATH);
			isr = new InputStreamReader(in, "utf-8");
			br = new BufferedReader(isr);
			confs = new HashMap<>();
			
			String line = null;
			while((line=br.readLine()) != null){
				if(!line.toString().trim().startsWith("#") && !line.toString().trim().equals("") ){
					String[] tmp = line.toString().trim().split("=");
					if(tmp.length==1){
						confs.put(tmp[0], "");
					}else{
						confs.put(tmp[0], tmp[1]);
					}
				}
			}
		} catch (Exception e) {
			logger.error("加载配置文件{}出错！", FILE_NAME, e);
			e.printStackTrace();
		}
		
		try {
			if(in!=null) in.close();
			if(isr!=null) isr.close();
			if(br!=null) br.close();
		} catch (Exception e) {
			logger.error("关闭{}的配置文件读取流出错！", FILE_NAME, e);
		} finally {
			try {
				in.close();
				isr.close();
				br.close();
			} catch (Exception e2) {
				logger.error("关闭{}的配置文件读取流出错！", FILE_NAME, e2);
			}
		}
		
		return confs;
	}
	
	/**
	 * 获取key对应的value
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		Map<String, String> confs = read();
		return confs.get(key);
	}
	
	/**
	 * 从wholeStr中剔除itemStr
	 * @param itemStr
	 * @param wholeStr
	 * @return
	 */
	public static String removeItemStr(String itemStr, String wholeStr){
		if(wholeStr.contains(itemStr)){
			wholeStr = wholeStr.replaceAll(itemStr, "");
		}
		if(wholeStr.trim().equals("")){
			return "";
		}
		String[] tmp = wholeStr.split(",");
		String resStr = "";
		for(int i=0;i<tmp.length;i++){
			resStr += (tmp[i]!=null && !tmp[i].equals(""))?(tmp[i]+","):"";
		}
		return resStr.substring(0, resStr.length()-1);
	}
	
	/**
	 * 更新key对应的配置内容
	 * @param key
	 * @param value
	 */
	public static void updateKay(String key, String value){
		//修改key之前先将原来的配置保存一份，后面修改结束之后需要把其他数据写入文件保存
		InputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		Map<Integer, String> datas = null;
		try {
			in = new FileInputStream(FILE_PATH);
			isr = new InputStreamReader(in, "utf-8");
			br = new BufferedReader(isr);
			datas = new HashMap<>();
			
			//读取内存并保存
			String line = null;
			Integer each = 0;
			while((line=br.readLine()) != null){
				each ++;
				if(!line.toString().trim().startsWith("#") && !line.toString().trim().equals("") ){
					String[] tmp = line.toString().trim().split("=");
					if(tmp[0].equals(key)){
						line = tmp[0]+"="+value;
					}
				}
				datas.put(each, line);
			}
		} catch (Exception e) {
			logger.error("准备更新配置文件{}时，加载出错！", FILE_NAME, e);
			e.printStackTrace();
		}
		
		//将更新之后的配置写入文件
		OutputStream os = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			os = new FileOutputStream(FILE_PATH);
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);
			int datasSize = datas.size();
			for(int i=1;i<datasSize+1;i++){
				bw.write(datas.get(i));
				bw.write("\n");
			}
			bw.flush();
		} catch (Exception e) {
			logger.error("重新写入配置文件{}出错！", FILE_NAME, e);
			e.printStackTrace();
		}
		
		//关闭所有流
		try {
			if(in!=null) in.close();
			if(isr!=null) isr.close();
			if(br!=null) br.close();
			if(os!=null) os.close();
			if(osw!=null) osw.close();
			if(bw!=null) bw.close();
		} catch (Exception e) {
			logger.error("关闭{}的配置文件读取流出错！", FILE_NAME, e);
		} finally {
			try {
				in.close();
				isr.close();
				br.close();
				os.close();
				osw.close();
				bw.close();
			} catch (Exception e2) {
				logger.error("关闭{}的配置文件读取流出错！", FILE_NAME, e2);
			}
		}
	}
	
	/**
	 * 自测
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("更新test前="+DemoConfigUtil.getValue("test"));
		DemoConfigUtil.updateKay("test", "1111,2222,3333,4444");
		System.out.println("更新test后="+DemoConfigUtil.getValue("test"));
		
//		String itemStr = "2222";
//		String oldStr = DemoConfigUtil.getValue("test");
//		String newStr = DemoConfigUtil.removeItemStr(itemStr, oldStr);
//		DemoConfigUtil.updateKay("test", newStr);
//		System.out.println("更新test后="+DemoConfigUtil.getValue("test"));
		
	}
	
	
}
