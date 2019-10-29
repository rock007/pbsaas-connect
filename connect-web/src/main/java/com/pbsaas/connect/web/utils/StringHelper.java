/**
*@Project: subway-backend
*@Author: sam
*@Date: 2016年12月13日
*@Copyright: 2016  All rights reserved.
*/
package com.pbsaas.connect.web.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.netty.buffer.ByteBuf;


/**
 * @author sam
 *
 */
public class StringHelper {

	public static String makeUid(){
		
		 UUID uuid = UUID.randomUUID();
		 
		 return uuid.toString();
	}
	
	public static String generateWord() { 
		
        String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",  
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",  
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
                "W", "X", "Y", "Z" };  
        List list = Arrays.asList(beforeShuffle);  
        Collections.shuffle(list);  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < list.size(); i++) {  
            sb.append(list.get(i));  
        }  
        String afterShuffle = sb.toString();  
        String result = afterShuffle.substring(5, 9);  
        return result;  
    }  
	
	public static boolean isMobileNO(String mobiles) {
		//Pattern p = Pattern.compile("^((1[0-9][0-9]))\\d{8}$");
		Pattern p = Pattern.compile("(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}");
		
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	public static String converTimerFormat(String m){
		
		String arr1[]=m.split(",");
		
		if(arr1.length != 2) return "";
		
		String date_arr[]=arr1[0].split(":");
		
		if(date_arr.length !=2 ) return "";
		
		String week_arr[]=arr1[1].split("");
		String week_str="";
		
		for(String w:week_arr){
			
			if(w.trim().equals(""))continue;
			
			week_str+=w+",";
		}
		
		if(week_str.length()>1){
			week_str=week_str.substring(0, week_str.length()-1);
		}else{
			week_str="* ";
		}
		
		return toNoZero(date_arr[1])+" "+toNoZero(date_arr[0])+" * * "+week_str;
	}
	
	private static String  toNoZero(String m){
		
		if(m.startsWith("0")) return m.substring(1);
		
		return m;
	}
	
	public static String  pre0x(String source){
		
		String arr1[]=source.split(",");
		String toStr="";
		
		for(String m:arr1){
			
			toStr=toStr+"0x"+m+",";
		}
		
		return toStr.length()>0?toStr.substring(0, toStr.length()-1):"";
		
	}
	
	public static Integer  sum2dec(String source){
		
		String arr1[]=source.split(",");
		Integer toSum=0;
		
		for(String m:arr1){
			
			toSum +=Integer.parseInt(m, 16);
		}
		
		return toSum ;
		
	}
	
	public static String and(String hex1,String hex2){
		
		Integer m1=Integer.parseInt(hex1, 16);
		Integer m2=Integer.parseInt(hex2, 16);
		
		Integer result= m1&m2;
		
		return String.format("%02x",result).toUpperCase();
	}
	
	public static String tohex(Integer m){
		
		String codeIndexHexStr=Integer.toHexString(m).toUpperCase();
		
		if(codeIndexHexStr.length()==1){
			return "0x00,0x0"+codeIndexHexStr;
		}else if(codeIndexHexStr.length()==2){
			return "0x00,0x"+codeIndexHexStr;			
		}else if(codeIndexHexStr.length()==3){
			
			return "0x0"+codeIndexHexStr.substring(0,1)+",0x"+codeIndexHexStr.substring(1);
			
		}else if(codeIndexHexStr.length()>=4){
			
			return "0x"+codeIndexHexStr.substring(0,2)+",0x"+codeIndexHexStr.substring(2,4);
			
		}
		return codeIndexHexStr;
	}
	
	
	public static String format(final String fmt, Object... params) {
		String f;
		if (params != null) {
			f = String.format(fmt, params);
		} else {
			f = fmt;
		}

		return f;
	}

	public static byte[] readByteBuf(ByteBuf buf) {

		byte[] bytes;
		int offset;
		int length = buf.readableBytes();

		if (buf.hasArray()) {
			bytes = buf.array();
			offset = buf.arrayOffset();
		} else {
			bytes = new byte[length];
			buf.getBytes(buf.readerIndex(), bytes);
			offset = 0;
		}
		return bytes;
	}
	
	public static void main(String[] args) throws Exception {
		
		String m1=converTimerFormat("01:01,0 1 2 3 4 5 6");
		System.out.println(m1);
		
		String m2=and("C0","0A");
		System.out.println(m2);
		
		int a=129;
		int b=128;
		
		
		System.out.println("a 和b 与的结果是："+(a&b));
		
		System.out.println("tohex(558)："+tohex(558));
		System.out.println("tohex(570)："+tohex(570));
			
	}
}
