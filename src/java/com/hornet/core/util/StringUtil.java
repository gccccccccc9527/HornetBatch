package com.hornet.core.util;

import java.text.DecimalFormat;

/**
 * <li>功能描述：字符串处理工具类
 * <li>作者：sunguanchun
 * <li>创建时间：2017年10月27日 下午5:21:33
 */
public final class StringUtil {
	
	 /**
	  * 字符串分隔符，用来分隔多个对象id，如：1,2,3,4,5
	  */
	  public static final String SEPARATOR_0 = ",";
	  public static final String SERARATOR_1 = "|";
	  public static final String SERARATOR_2 = "||";
	  public static final String SERARATOR_3 = "&";
	  public static final String SERARATOR_4 = "/";
	  public static final String SERARATOR_5 = "\\";
	  public static final String SERARATOR_6 = "//";
	  public static final String SERARATOR_7 = "?";
	  public static final String SERARATOR_8 = "=";
	  public static final String SERARATOR_9 = ".";
	  public static final String SERARATOR_10 = "-";
	  public static final String SERARATOR_11 = ":";
	  public static final String SERARATOR_12 = "://";
	  public static final String SERARATOR_13 = "\"";
	  public static final String SERARATOR_14 = "\\\"";
	  public static final String SERARATOR_15 = ";";
	  public static final String SERARATOR_16 = "(";
	  public static final String SERARATOR_17 = ")";
	  public static final String SERARATOR_18 = "'";
	  public static final String SERARATOR_19 = "\"";
	  public static final String SERARATOR_20 = "%";
	  public static final String SERARATOR_21 = "'%";
	  public static final String SERARATOR_24 = "%'";
	  public static final String SERARATOR_22 = "{";
	  public static final String SERARATOR_23 = "}";
	
	/**
	 * 使用DecimalFormat类对科学计数法格式的数字进行格式化
	 */
	private static DecimalFormat df = null;
	
	/**
	  * 类的私有构造函数，防止类被实例化
	  */
	private StringUtil(){}
	
	
	/**
	 * 验证字符串是否为空
	 * @param str
	 */
	public static final boolean isNull(String str){
		if(null == str || str.trim().length() == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * 将字符串中的单引号、双引号进行转义
	 */
	public static final String toReplace(String str){
		if(isNull(str))
			throw new NullPointerException("需要转译的字符串不可以为空");
		return str.replace(SERARATOR_18,"\\'" ).replace(SERARATOR_19, "\\\"");
	}

	/**
	 * 将字符串拼接成Like查询需要的格式
	 * @param str
	 * @return
	 */
	public static final String toLikeStr(String str){
		if(isNull(str))
			throw new NullPointerException("需要转译的字符串不可以为空");
		return SERARATOR_20+toReplace(str)+SERARATOR_20;
	}
	
	/**
	 * 验证是否有指定的字符或字符串，如果有则返回字符所在的索引
	 * 如果是字符串，则返回字符串第一个字符的索引
	 * @param str 待验证的字符串
	 * @param validateStr 验证的字符或字符串 
	 * @return
	 */
	public static final int validateStr(String validateStr,String str){
		return validateStr.indexOf(str);
	}
	
	/**
	 * 验证是否存在指定字符，并截取指定长度的字符串，
	 * 如果是判断小数点，则需要传入小数点后的位数
	 * @param validateStr  验证的字符或字符串 
	 * @param str  待验证的字符串
	 * @seffixNum 小数点后的长度
	 * 
	 */
	public static final String validateAndSubstring(String validateStr,String str,int seffixNum){
		int index = validateStr(validateStr,str);
		if(index == -1){
			return validateStr;
		}else{
			if(str.equals(SERARATOR_9)){
				if(seffixNum == 0)
					return validateStr.substring(0,index);
				else
					if(validateStr.length() < index+seffixNum+1)
						return validateStr;
					else
						return validateStr.substring(0,index+seffixNum+1);
			}else{
				return validateStr.substring(0,index+str.length());
			}
		}
	}
	
	/**
	 * 将科学计数法的数字转换成正常形式
	 * @param numberStr 采用科学计数法的数字
	 * @return
	 */
	public static String numberToStr(double numberStr){
		if(null == df)
			df = new DecimalFormat("0");
		return df.format(numberStr);
	}
	
	/**
	 * 将字节数组中的数据 转换为字符串
	 * @param data
	 * @return
	 */
	public static String  showByteArray(byte[] data){
        if(null == data){
            throw new NullPointerException("需要转换的数据不可以为空");
        }
        StringBuilder sb = new StringBuilder(SERARATOR_22);
        for(byte b:data){
            sb.append(b).append(SEPARATOR_0);
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(SERARATOR_23);
        return sb.toString();
    }

	/**
	 * 判断奇数偶数
	 * 奇数=false 偶数=true
	 * @return
	 */
	public static boolean isOddOrEvenNum(String num) {
		int n = Integer.parseInt(num);
		if(n%2==0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据身份证判断性别
	 * 男=true 女=false
	 * @param idNo
	 * @return
	 */
	public static boolean checkSex(String idNo) {
		String check = idNo.substring(14,15);
		if(isOddOrEvenNum(check)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 截取身份证获取生日
	 * @param idNo
	 * @return
	 */
	public static String getBirthFromCertNo(String idNo) {
		return idNo.substring(6,14);
	}
	
	/**
	 * 截取身份证获取生日
	 * @param idNo
	 * @return
	 */
	public static String getBirthFromCertNo(String idNo, String spl) {
		String temp = idNo.substring(6,14);
		return temp.substring(0,4)+spl+temp.substring(4,6)+spl+temp.substring(6,8);
	}
	

}
