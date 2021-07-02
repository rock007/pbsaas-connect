package com.pbsaas.connect.model.vo;

import java.io.Serializable;

public class ResultVO<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -366079256795290338L;
	
	private int result;
	private String msg;
	
	private T data;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public ResultVO(int b,String m){
		
		this.result=b;
		this.msg=m;
	}
	
	public ResultVO(int b,String m,T data){
		
		this.result=b;
		this.msg=m;
		this.data=data;
	}
	
	public ResultVO(){
		
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
