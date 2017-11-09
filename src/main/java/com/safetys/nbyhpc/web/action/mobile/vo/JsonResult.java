package com.safetys.nbyhpc.web.action.mobile.vo;

import java.io.Serializable;

import com.safetys.framework.domain.model.pagination.Pagination;

public class JsonResult implements Serializable {

	private static final long serialVersionUID = -1279556626612645660L;

	private boolean success = true;//是否成功
	
	private String msg;//普通文字描述信息（如要获取的数据、中文提示等）
	
	private String identify;//标识符描述信息（如英文字符串标识符）
	
	private long totalCount;//表单数据总数
	
	private Pagination page;//列表数据
	
	private Object data;//json字符串接收
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public Pagination getPage() {
		return page;
	}

	public void setPage(Pagination page) {
		this.page = page;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
