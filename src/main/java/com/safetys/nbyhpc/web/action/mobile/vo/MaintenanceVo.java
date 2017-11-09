package com.safetys.nbyhpc.web.action.mobile.vo;

/**
 * 维护码表
 * @author yangb
 *
 */
public class MaintenanceVo extends MobileVo {

	private static final long serialVersionUID = -6249730657061759899L;

	private Long fatherId;
	
	private String code;
	
	private String name;

	public Long getFatherId() {
		return fatherId;
	}

	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
