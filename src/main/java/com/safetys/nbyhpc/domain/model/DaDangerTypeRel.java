package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaDangerTypeRel.java
 * @date 2009-08-10
 * @author huangjl
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 */
public class DaDangerTypeRel extends DaBaseModel {

	/**
	 * 重大隐患与隐患类型中间表
	 */
	private static final long serialVersionUID = -3162476386176263118L;

	//重大隐患id
	private Long   praDaDanId;
	//一级隐患类型id
	private Long   parDaIndId;
	//二级隐患类型id
	private Long   parDaSecondIndId;



	public DaDangerTypeRel() {
	}



	public Long getPraDaDanId() {
		return praDaDanId;
	}



	public void setPraDaDanId(Long praDaDanId) {
		this.praDaDanId = praDaDanId;
	}



	public Long getParDaIndId() {
		return parDaIndId;
	}



	public void setParDaIndId(Long parDaIndId) {
		this.parDaIndId = parDaIndId;
	}



	public Long getParDaSecondIndId() {
		return parDaSecondIndId;
	}



	public void setParDaSecondIndId(Long parDaSecondIndId) {
		this.parDaSecondIndId = parDaSecondIndId;
	}


}
