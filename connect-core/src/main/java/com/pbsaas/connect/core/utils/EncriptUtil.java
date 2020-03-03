package com.pbsaas.connect.core.utils;

import java.security.MessageDigest;

public class EncriptUtil { 
	
    //十六进制下数字到字符的映射数组
    private final static String[] hexDigits = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"}; 
    /**把inputString加密*/
    public static String md5(String inputStr){ 
        return encodeByMD5(inputStr); 
    } 
    
    public static String MD54Pwd(String s) {
		
		if (s==null||s.length()==0){
			return null;
		}
		char hexDigits[] = { 'A', '1', 'B', '3', 'C', '5', 'D', '7', 'E','9', 'F', '0', 'G', '2', 'H', '4' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
    
    /** 
    * 验证输入的密码是否正确
    * @param password 真正的密码（加密后的真密码）
    * @param inputString 输入的字符串
    * @return验证结果，boolean类型
    */
    public static boolean authenticatePassword(String password,String inputString){ 
        if(password.equals(encodeByMD5(inputString))){ 
            return true; 
        }else{ 
            return false; 
        } 
    } 
    
    /**对字符串进行MD5编码*/
    private static String encodeByMD5(String originString){ 
        if (originString!=null) { 
            try { 
                //创建具有指定算法名称的信息摘要
                MessageDigest md5 = MessageDigest.getInstance("MD5"); 
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md5.digest(originString.getBytes()); 
                //将得到的字节数组变成字符串返回  
                String result = byteArrayToHexString(results); 
                return result; 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
        } 
        return null; 
    } 
    
    /** 
    * 轮换字节数组为十六进制字符串
    * @param b 字节数组
    * @return十六进制字符串
*/
    private static String byteArrayToHexString(byte[] b){ 
        StringBuffer resultSb = new StringBuffer(); 
        for(int i=0;i<b.length;i++){ 
            resultSb.append(byteToHexString(b[i])); 
        } 
        return resultSb.toString(); 
    } 
    //将一个字节转化成十六进制形式的字符串
    private static String byteToHexString(byte b){ 
        int n = b; 
        if(n<0) 
        n=256+n; 
        int d1 = n/16; 
        int d2 = n%16; 
        return hexDigits[d1] + hexDigits[d2]; 
    } 
}