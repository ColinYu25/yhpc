package com.safetys.nbyhpc.domain.model;

import java.io.Serializable;

/**
 * 
 * @author yinghui.zhang
 * @E-mail uuhui@163.com
 * @date 创建时间: 2013年9月30日
 * @Description: 企业信息数据交互中间表类
 * @Modify 修改人：
 * @date 修改时间：
 * @modifyContent
 */
public class ETLAqsc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long COMPANY_ID; // 编号
	private String COMPANY_UUID;// UUID
	private String COMPANY_NAME;// 企业名称
	private String SECURITY_PRINCIPAL_PERSON;// 安管负责人
	private String SECURITY_PRINCIPAL_TELEPHONE;// 安管负责人电话
	private Long SYN_NO;// 同步号
	private String AQSC_ID;

	
	
	public Long getCOMPANY_ID() {
		return COMPANY_ID;
	}
	public void setCOMPANY_ID(Long cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	public String getCOMPANY_UUID() {
		return COMPANY_UUID;
	}
	public void setCOMPANY_UUID(String cOMPANY_UUID) {
		COMPANY_UUID = cOMPANY_UUID;
	}
	public String getCOMPANY_NAME() {
		return COMPANY_NAME;
	}
	public void setCOMPANY_NAME(String cOMPANY_NAME) {
		COMPANY_NAME = cOMPANY_NAME;
	}
	public String getSECURITY_PRINCIPAL_PERSON() {
		return SECURITY_PRINCIPAL_PERSON;
	}
	public void setSECURITY_PRINCIPAL_PERSON(String sECURITY_PRINCIPAL_PERSON) {
		SECURITY_PRINCIPAL_PERSON = sECURITY_PRINCIPAL_PERSON;
	}
	public String getSECURITY_PRINCIPAL_TELEPHONE() {
		return SECURITY_PRINCIPAL_TELEPHONE;
	}
	public void setSECURITY_PRINCIPAL_TELEPHONE(String sECURITY_PRINCIPAL_TELEPHONE) {
		SECURITY_PRINCIPAL_TELEPHONE = sECURITY_PRINCIPAL_TELEPHONE;
	}
	public Long getSYN_NO() {
		return SYN_NO;
	}
	public void setSYN_NO(Long sYN_NO) {
		SYN_NO = sYN_NO;
	}
	
	public String getAQSC_ID() {
		return AQSC_ID;
	}
	public void setAQSC_ID(String aQSC_ID) {
		AQSC_ID = aQSC_ID;
	}
	

}
