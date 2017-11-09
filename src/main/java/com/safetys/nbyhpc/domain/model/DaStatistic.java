package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaStatistic.java
 * @date 2009-07-29
 * @author huangjl
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaStatistic extends DaBaseModel {

	private static final long serialVersionUID = 805467517225407390L;

	private String sId;//行业名称
	private String enumName;//行业名称
	private Integer companyNum;//实际排查治理事故隐患的生产经营单位
	private Integer troubNum;//排查一般事故隐患数
	private Integer troubCleanNum;//排查一般事故隐患整改数
	private Integer bigTroubNum;//排查重大事故隐患数
	private Integer bigTroubCleanNum;//列入治理计划的重大事故隐患
	private Integer target;//
	private Integer goods;//
	private Integer sResource;//
	private Integer finishDate;//
	private Integer safetyMethod;//
	private Integer ddng5Num;//达到“五到位”要求的
	private Integer provicercNum;//省级挂牌督办
	private Integer cityrcNum;//地市级挂牌督办
	private Integer allcompanyNum;//应排查治理事故隐患的生产经营单位
	private Integer qtrcNum;//其他挂牌督办
	private Double governMoney;//重大隐患累计落实隐患治理资金
	private Double normalgovernMoney;//一般隐患累计落实隐患治理资金
	private String stype;//发送报表类型（1：代表煤矿、金属非金属矿山等工矿企业安全生产事故隐患排查治理情况月报表；2：代表交通运输等重点行业领域企业和单位安全生产事故隐患排查治理情况月报表）
	private Integer sendMonth;//报表发送年月
	private String catchName;//缓存名称

	/**
	 * @return the enumName
	 */
	public String getEnumName() {
		return enumName;
	}

	/**
	 * @param enumName
	 *            the enumName to set
	 */
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	/**
	 * @return the companyNum
	 */
	public Integer getCompanyNum() {
		return companyNum;
	}

	/**
	 * @param companyNum
	 *            the companyNum to set
	 */
	public void setCompanyNum(Integer companyNum) {
		this.companyNum = companyNum;
	}

	/**
	 * @return the troubNum
	 */
	public Integer getTroubNum() {
		return troubNum;
	}

	/**
	 * @param troubNum
	 *            the troubNum to set
	 */
	public void setTroubNum(Integer troubNum) {
		this.troubNum = troubNum;
	}

	/**
	 * @return the troubCleanNum
	 */
	public Integer getTroubCleanNum() {
		return troubCleanNum;
	}

	/**
	 * @param troubCleanNum
	 *            the troubCleanNum to set
	 */
	public void setTroubCleanNum(Integer troubCleanNum) {
		this.troubCleanNum = troubCleanNum;
	}

	/**
	 * @return the bigTroubNum
	 */
	public Integer getBigTroubNum() {
		return bigTroubNum;
	}

	/**
	 * @param bigTroubNum
	 *            the bigTroubNum to set
	 */
	public void setBigTroubNum(Integer bigTroubNum) {
		this.bigTroubNum = bigTroubNum;
	}

	/**
	 * @return the bigTroubCleanNum
	 */
	public Integer getBigTroubCleanNum() {
		return bigTroubCleanNum;
	}

	/**
	 * @param bigTroubCleanNum
	 *            the bigTroubCleanNum to set
	 */
	public void setBigTroubCleanNum(Integer bigTroubCleanNum) {
		this.bigTroubCleanNum = bigTroubCleanNum;
	}

	/**
	 * @return the target
	 */
	public Integer getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(Integer target) {
		this.target = target;
	}

	/**
	 * @return the goods
	 */
	public Integer getGoods() {
		return goods;
	}

	/**
	 * @param goods
	 *            the goods to set
	 */
	public void setGoods(Integer goods) {
		this.goods = goods;
	}

	/**
	 * @return the sResource
	 */
	public Integer getsResource() {
		return sResource;
	}

	/**
	 * @param sResource
	 *            the sResource to set
	 */
	public void setsResource(Integer sResource) {
		this.sResource = sResource;
	}

	/**
	 * @return the finishDate
	 */
	public Integer getFinishDate() {
		return finishDate;
	}

	/**
	 * @param finishDate
	 *            the finishDate to set
	 */
	public void setFinishDate(Integer finishDate) {
		this.finishDate = finishDate;
	}

	/**
	 * @return the safetyMethod
	 */
	public Integer getSafetyMethod() {
		return safetyMethod;
	}

	/**
	 * @param safetyMethod
	 *            the safetyMethod to set
	 */
	public void setSafetyMethod(Integer safetyMethod) {
		this.safetyMethod = safetyMethod;
	}

	/**
	 * @return the ddng5Num
	 */
	public Integer getDdng5Num() {
		return ddng5Num;
	}

	/**
	 * @param ddng5Num
	 *            the ddng5Num to set
	 */
	public void setDdng5Num(Integer ddng5Num) {
		this.ddng5Num = ddng5Num;
	}

	/**
	 * @return the provicercNum
	 */
	public Integer getProvicercNum() {
		return provicercNum;
	}

	/**
	 * @param provicercNum
	 *            the provicercNum to set
	 */
	public void setProvicercNum(Integer provicercNum) {
		this.provicercNum = provicercNum;
	}

	/**
	 * @return the cityrcNum
	 */
	public Integer getCityrcNum() {
		return cityrcNum;
	}

	/**
	 * @param cityrcNum
	 *            the cityrcNum to set
	 */
	public void setCityrcNum(Integer cityrcNum) {
		this.cityrcNum = cityrcNum;
	}

	/**
	 * @return the allcompanyNum
	 */
	public Integer getAllcompanyNum() {
		return allcompanyNum;
	}

	/**
	 * @param allcompanyNum
	 *            the allcompanyNum to set
	 */
	public void setAllcompanyNum(Integer allcompanyNum) {
		this.allcompanyNum = allcompanyNum;
	}

	/**
	 * @return the qtrcNum
	 */
	public Integer getQtrcNum() {
		return qtrcNum;
	}

	/**
	 * @param qtrcNum
	 *            the qtrcNum to set
	 */
	public void setQtrcNum(Integer qtrcNum) {
		this.qtrcNum = qtrcNum;
	}

	/**
	 * @return the stype
	 */
	public String getStype() {
		return stype;
	}

	/**
	 * @param stype
	 *            the stype to set
	 */
	public void setStype(String stype) {
		this.stype = stype;
	}

	/**
	 * @return the catchName
	 */
	public String getCatchName() {
		return catchName;
	}

	/**
	 * @param catchName
	 *            the catchName to set
	 */
	public void setCatchName(String catchName) {
		this.catchName = catchName;
	}


	/**
	 * @return the sendMonth
	 */
	public Integer getSendMonth() {
		return sendMonth;
	}

	/**
	 * @param sendMonth
	 *            the sendMonth to set
	 */
	public void setSendMonth(Integer sendMonth) {
		this.sendMonth = sendMonth;
	}

	/**
	 * @return the governMoney
	 */
	public Double getGovernMoney() {
		return governMoney;
	}

	/**
	 * @param governMoney the governMoney to set
	 */
	public void setGovernMoney(Double governMoney) {
		this.governMoney = governMoney;
	}

	/**
	 * @return the normalgovernMoney
	 */
	public Double getNormalgovernMoney() {
		return normalgovernMoney;
	}

	/**
	 * @param normalgovernMoney the normalgovernMoney to set
	 */
	public void setNormalgovernMoney(Double normalgovernMoney) {
		this.normalgovernMoney = normalgovernMoney;
	}

	/**
	 * @return the sId
	 */
	public String getsId() {
		return sId;
	}

	/**
	 * @param sId the sId to set
	 */
	public void setsId(String sId) {
		this.sId = sId;
	}

}
