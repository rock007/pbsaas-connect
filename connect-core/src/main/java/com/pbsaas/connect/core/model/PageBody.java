package com.pbsaas.connect.core.model;

import java.util.List;

public class PageBody<T> {
	
	private int result;
	
	private String msg;
	
	private int totalPages;
	
	private int totalRows;

	private int pageSize=20;
	private int pageIndex=1;

	private List<T> data;

	public PageBody() {
		super();
		this.result = -1;
		this.msg = "";
		this.totalPages=1;
		this.totalRows=0;
	}

	public PageBody(int result, String msg) {
		super();
		this.result = result;
		this.msg = msg;
		this.totalPages=1;
		this.totalRows=0;
	}
	
	public PageBody(int result, String msg, List<T> data) {
		super();
		this.result = result;
		this.msg = msg;
		this.data = data;
		this.totalPages=1;
		this.totalRows=data==null?0:data.size();
		
	}

	public PageBody(int result, String msg, int totalPages, int totalRows, List<T> data) {
		super();
		this.result = result;
		this.msg = msg;
		this.totalPages = totalPages;
		this.totalRows = totalRows;
		this.data = data;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
}
