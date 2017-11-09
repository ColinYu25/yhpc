package com.safetys.framework.web.model;


/**
 * @Author:seak.lv
 * @MSN:seak82@hotmail.com
 * @Email:<a href="mailto:seaklv@sina.com">seaklv@sina.com</a>
 * @Created:2011-8-3
 * @Modified:xxxx-xx-xx
 */
public class ResponseMessage {
	
	private String msg;
	
	private boolean success=true;

	public String getMsg() {
		return msg;
	}
	public boolean isSuccess() {
		return success;
	}
	public void write(String msg,boolean success) {
		this.msg = msg;
		this.success=success;
	}
}
