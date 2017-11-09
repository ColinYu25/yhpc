package com.safetys.nbyhpc.web.action.mobile.vo;


public class UserVo extends MobileVo {

	private static final long serialVersionUID = -6683440500966700031L;

	private String password;
	
	private String userPhone; //联系手机
	
	private String oldPassword; //旧密码

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
