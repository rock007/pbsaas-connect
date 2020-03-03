package com.pbsaas.connect.core.model;

import java.io.Serializable;

public class JsonBody<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7058370188973768265L;
	private int result;
	private String msg;
	
	private T data;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public JsonBody(int b,String m){
		
		this.result=b;
		this.msg=m;
	}
	
	public JsonBody(int b,String m,T data){
		
		this.result=b;
		this.msg=m;
		this.data=data;
	}
	
	public JsonBody(){
		
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
}
