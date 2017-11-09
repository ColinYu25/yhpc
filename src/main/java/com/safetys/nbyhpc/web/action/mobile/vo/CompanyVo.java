package com.safetys.nbyhpc.web.action.mobile.vo;

import java.util.Set;

import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;

public class CompanyVo extends MobileVo {

	private static final long serialVersionUID = 3323004366038790386L;

	private String companyName; //企业名称
	
	private String setupNumber; //组织机构代码
	
	private String regNum; //统一信用代码
	
	private String tradeTypeText; //所属行业
	
	private String firstAreaName; //区域中文名
	private String secondAreaName;//区域中文名
	private String thirdAreaName;//区域中文名
	private String fouthAreaName;//区域中文名
	//------------------------------以上是只读-------------------
	
	private String regAddress; //注册地址
	
	private String address; //所在地址
	
	private Long firstArea;

	private Long secondArea;

	private Long thirdArea;

	private Long fouthArea;
	
	private String economicType1;//经济类型1
	private String economicType2;//经济类型2
	
	private String naEcoType1;//国民经济分类1
	private String naEcoType2;//国民经济分类2
	
	private String fddelegate; //法人代表
	
	private String safetyMngPerson;//安管负责人
	
	private String safetyMngPersonPhone;//安管负责人电话
	
	private String productionScale; //企业规模
	
	private String businessScope; //经营范围
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public String getSetupNumber() {
		return setupNumber;
	}

	public void setSetupNumber(String setupNumber) {
		this.setupNumber = setupNumber;
	}

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	public String getFddelegate() {
		return fddelegate;
	}

	public void setFddelegate(String fddelegate) {
		this.fddelegate = fddelegate;
	}
	
	public String getTradeTypeText() {
		return tradeTypeText;
	}

	public void setTradeTypeText(String tradeTypeText) {
		this.tradeTypeText = tradeTypeText;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getFirstArea() {
		return firstArea;
	}

	public void setFirstArea(Long firstArea) {
		this.firstArea = firstArea;
	}

	public Long getSecondArea() {
		return secondArea;
	}

	public void setSecondArea(Long secondArea) {
		this.secondArea = secondArea;
	}

	public Long getThirdArea() {
		return thirdArea;
	}

	public void setThirdArea(Long thirdArea) {
		this.thirdArea = thirdArea;
	}

	public Long getFouthArea() {
		return fouthArea;
	}

	public void setFouthArea(Long fouthArea) {
		this.fouthArea = fouthArea;
	}

	public String getEconomicType1() {
		return economicType1;
	}

	public void setEconomicType1(String economicType1) {
		this.economicType1 = economicType1;
	}

	public String getEconomicType2() {
		return economicType2;
	}

	public void setEconomicType2(String economicType2) {
		this.economicType2 = economicType2;
	}

	public String getNaEcoType1() {
		return naEcoType1;
	}

	public void setNaEcoType1(String naEcoType1) {
		this.naEcoType1 = naEcoType1;
	}

	public String getNaEcoType2() {
		return naEcoType2;
	}

	public void setNaEcoType2(String naEcoType2) {
		this.naEcoType2 = naEcoType2;
	}

	public String getSafetyMngPerson() {
		return safetyMngPerson;
	}

	public void setSafetyMngPerson(String safetyMngPerson) {
		this.safetyMngPerson = safetyMngPerson;
	}

	public String getSafetyMngPersonPhone() {
		return safetyMngPersonPhone;
	}

	public void setSafetyMngPersonPhone(String safetyMngPersonPhone) {
		this.safetyMngPersonPhone = safetyMngPersonPhone;
	}

	public String getProductionScale() {
		return productionScale;
	}

	public void setProductionScale(String productionScale) {
		this.productionScale = productionScale;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getFirstAreaName() {
		return firstAreaName;
	}

	public void setFirstAreaName(String firstAreaName) {
		this.firstAreaName = firstAreaName;
	}

	public String getSecondAreaName() {
		return secondAreaName;
	}

	public void setSecondAreaName(String secondAreaName) {
		this.secondAreaName = secondAreaName;
	}

	public String getThirdAreaName() {
		return thirdAreaName;
	}

	public void setThirdAreaName(String thirdAreaName) {
		this.thirdAreaName = thirdAreaName;
	}

	public String getFouthAreaName() {
		return fouthAreaName;
	}

	public void setFouthAreaName(String fouthAreaName) {
		this.fouthAreaName = fouthAreaName;
	}

	public void buildByCompany(DaCompany company, boolean buildTradeType) {
		super.buildVo(company);
		this.companyName = company.getCompanyName();
		this.setupNumber = company.getSetupNumber();
		this.regNum = company.getRegNum();
		if (buildTradeType) {
			Set<DaIndustryParameter> tradeTypes = company.getHzTradeTypes();
			if (tradeTypes != null && tradeTypes.size() > 0) {
				String tradeType1 = "";
				String tradeType2 = "";
				String tradeType3 = "";
				for (DaIndustryParameter daIndustryParameter : tradeTypes) {
					if (Integer.valueOf(0).equals(daIndustryParameter.getGradeRate())) {
						tradeType1 = daIndustryParameter.getName();
					} else if (Integer.valueOf(1).equals(daIndustryParameter.getGradeRate())) {
						tradeType2 = daIndustryParameter.getName();
					} else if (Integer.valueOf(2).equals(daIndustryParameter.getGradeRate())
							&& daIndustryParameter.getCode() != null
							&& !daIndustryParameter.getCode().contains("_undefined")) {
						tradeType3 = daIndustryParameter.getName();
					}
				}
				this.tradeTypeText = tradeType1 + " " + tradeType2 + " " + tradeType3;
			}
		}
		this.regAddress = company.getRegAddress();
		this.address = company.getAddress();
		this.firstArea = company.getFirstArea();
		this.secondArea = company.getSecondArea();
		this.thirdArea = company.getThirdArea();
		this.fouthArea = company.getFouthArea();
		this.economicType1 = company.getEconomicType1();
		this.economicType2 = company.getEconomicType2();
		this.naEcoType1 = company.getNaEcoType1();
		this.naEcoType2 = company.getNaEcoType2();
		this.fddelegate = company.getFddelegate();
		this.safetyMngPerson = company.getSafetyMngPerson();
		this.safetyMngPersonPhone = company.getSafetyMngPersonPhone();
		this.productionScale = company.getProductionScale();
		this.businessScope = company.getBusinessScope();
	}
	
}
