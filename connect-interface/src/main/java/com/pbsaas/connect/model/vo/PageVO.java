package com.pbsaas.connect.model.vo;

import java.io.Serializable;
import java.util.List;

public class PageVO<T>  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6260288242188471912L;
	
	private int pageIndex;// 索引
	private int pageSize;// 每页显示条数

	private int totalPage;// 总页数

	private int dataCount;// 总记录数
	
	private List<T> data;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getDataCount() {
		return dataCount;
	}

	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
