package com.safetys.nbyhpc.vo;


public class TCompanyVo   implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2864687432466542895L;
	private Long id;
	private String companyName;
	private String businessRegNum;
	private String orgCode;
	private String uuid;
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the businessRegNum
	 */
	public String getBusinessRegNum() {
		return businessRegNum;
	}
	/**
	 * @param businessRegNum the businessRegNum to set
	 */
	public void setBusinessRegNum(String businessRegNum) {
		this.businessRegNum = businessRegNum;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
