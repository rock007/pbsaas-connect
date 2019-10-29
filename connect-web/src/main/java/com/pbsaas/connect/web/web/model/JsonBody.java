package com.pbsaas.connect.web.web.model;

/**
 * @author sam
 *
 */
public class JsonBody<T> {

	private int result;
	
	private String msg;
	
	private T data;
	
	public JsonBody(int result, String msg) {
		
		this.result = result;
		this.msg = msg;
	}
	
	public JsonBody(int result, String msg, T data) {
		
		this.result = result;
		this.msg = msg;
		this.data = data;
	}



	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}
