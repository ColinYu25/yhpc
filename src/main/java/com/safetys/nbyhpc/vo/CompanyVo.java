package com.safetys.nbyhpc.vo;

/**
 * 
 * @author yangb
 *
 */
public class CompanyVo {

	private String phoneCode; //法定代表人或主要负责人联系电话
	
	private String safetyMngPersonPhone;//安管负责人电话

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getSafetyMngPersonPhone() {
		return safetyMngPersonPhone;
	}

	public void setSafetyMngPersonPhone(String safetyMngPersonPhone) {
		this.safetyMngPersonPhone = safetyMngPersonPhone;
	}
	
}
