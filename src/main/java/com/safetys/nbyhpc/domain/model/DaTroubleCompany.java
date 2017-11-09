package com.safetys.nbyhpc.domain.model;

import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaTroubleCompany extends DaBaseModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4808757447622802747L;

	private String troubleCompanyName;
	
	private String fddelegate;
	
	private String principal;
	
	private String linkTel;
	
	private String linkMobile;

	private Long userId;
	
	private Set<DaTrouble> daTroubles = new HashSet<DaTrouble>(0);

	public DaTroubleCompany() {

	}

	public Set<DaTrouble> getDaTroubles() {
		return daTroubles;
	}



	public void setDaTroubles(Set<DaTrouble> daTroubles) {
		this.daTroubles = daTroubles;
	}



	public String getFddelegate() {
		return fddelegate;
	}



	public void setFddelegate(String fddelegate) {
		this.fddelegate = fddelegate;
	}



	public String getLinkMobile() {
		return linkMobile;
	}



	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}



	public String getLinkTel() {
		return linkTel;
	}



	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}



	public String getPrincipal() {
		return principal;
	}



	public void setPrincipal(String principal) {
		this.principal = principal;
	}



	public String getTroubleCompanyName() {
		return troubleCompanyName;
	}



	public void setTroubleCompanyName(String troubleCompanyName) {
		this.troubleCompanyName = troubleCompanyName;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
