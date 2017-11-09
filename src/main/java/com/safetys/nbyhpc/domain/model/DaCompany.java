package com.safetys.nbyhpc.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;
import com.safetys.nbyhpc.web.action.mobile.vo.CompanyVo;

/**
 * @(#) DaCompany.java
 * @date 2009-07-29
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaCompany extends DaBaseModel {

	/**
	 * 企业实体
	 */
	private static final long serialVersionUID = 805467517225407390L;

	private DaCompanyPass daCompanyPass;   
	
	private DaCompanyLogout daCompanyLogout;
	
	private String companyName;

	private String regAddress;
	
	private String setupNumber;

	private String regNum;

	private String fddelegate;

	private String phoneCode;

	private Integer type;

	private Long firstArea;

	private Long secondArea;

	private Long thirdArea;

	private Long fouthArea;

	private Long fifthArea;

	private Long userId;
	
	private int isRepairCount;
	
	private int notRepairCount;

	private Set<DaCompanyAcount> daCompanyAcounts = new HashSet<DaCompanyAcount>(0);

	private Set<DaIndustryParameter> hzTradeTypes = new HashSet<DaIndustryParameter>(0);
	
	private Set<DaPointType> daPointTypes = new HashSet<DaPointType>(0);

	private String orderProperty;// 排序字段

	private boolean orderType;// 排序类型（升序、降序）

	private String tradeTypes;// 行业类型字符串，用于添加、修改企业信息及搜索企业列表
	
	private boolean seasonOne=false; //判断是否存在第一集报表
	
	private boolean seasonTwo=false;//判断是否存在第二集报表
	
	private boolean seasonThree=false;//判断是否存在第三集报表
	
	private boolean seasonFour=false;//判断是否存在第四集报表
	
	private Integer affrim;//搜索条件 是否确认
	
	private Integer off;//搜索条件 是否确认
	
	private String companyTrade;
	
	private String userName;
	
	private int industryId;
	
	private String companyLevel;

	private Integer companyPoint;
	
	private int year;
	
	private int month;
	
	private int quarter;
	
	private Date startTime;
	
	private Date endTime;
	
	private Integer employeeNumber;//EMPLOYEE_NUMBER;职工人数
	
	private String businessScope;//BUSINESS_SCOPE;经营范围
	
	private Integer focusFireUnits;//IS_FOCUS_FIRE_UNITS;是否重点消防单位
	
	private  String  uuid;
	
	private  String  flag;  //注册时判断是否帐号已存在  1已存在  2 新增 返回帐号
	
	private String productionScale;  //生产规模
	//企业是否修改过（0，没有修改过；1，修改过）
	private String address;//所在地址
	
	private Date establishmentDay;//成立时间
	
	private String economicType1;//经济类型1
	private String economicType2;//经济类型2
	
	private String tradeType1;//行业分类1(作废字段)
	private String tradeType2;//行业分类2(作废字段)
	
	private String naEcoType1;//国民经济分类1
	private String naEcoType2;//国民经济分类2
	
	
	private String safetyMngPerson;//安管负责人
	private String safetyMngPersonPhone;//安管负责人电话
	
	private String govAdminOrg;//安全生产政府主管单位
	private String companyCode;//企业内部编码
	//企业是否修改过（0，没有修改过；1，修改过）
	private Integer isModify;
	
	
	// 是否有工商注册hao
	private String haveRegNum;
	private Long isSYN;// 是否与本地交互 1-交互 0-否
	private String staticDate;
	
	//是否高风险作业（1：是，0：否）
	private String isHighRiskWork;
	//高风险作业内容
	private String highRiskWork;
	
	
	private String state;
	
	//构造函数
	public DaCompany(Long id, String companyName, String regAddress) {
		super(id);
		this.companyName = companyName;
		this.regAddress = regAddress;
	}

	public int getQuarter() {
		return quarter;
	}

	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getIndustryId() {
		return industryId;
	}

	public void setIndustryId(int industryId) {
		this.industryId = industryId;
	}

	public String getCompanyTrade() {
		return companyTrade;
	}

	public void setCompanyTrade(String companyTrade) {
		this.companyTrade = companyTrade;
	}

	public boolean isSeasonOne() {
		return seasonOne;
	}

	public void setSeasonOne(boolean seasonOne) {
		this.seasonOne = seasonOne;
	}

	public boolean isSeasonTwo() {
		return seasonTwo;
	}

	public void setSeasonTwo(boolean seasonTwo) {
		this.seasonTwo = seasonTwo;
	}

	public boolean isSeasonThree() {
		return seasonThree;
	}

	public void setSeasonThree(boolean seasonThree) {
		this.seasonThree = seasonThree;
	}

	public boolean isSeasonFour() {
		return seasonFour;
	}

	public void setSeasonFour(boolean seasonFour) {
		this.seasonFour = seasonFour;
	}

	public DaCompany() {
	}

	public DaCompany(Long id) {
		super(id);
	}

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	public String getFddelegate() {
		return this.fddelegate;
	}

	public void setFddelegate(String fddelegate) {
		this.fddelegate = fddelegate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
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

	public Set<DaCompanyAcount> getDaCompanyAcounts() {
		return daCompanyAcounts;
	}

	public void setDaCompanyAcounts(Set<DaCompanyAcount> daCompanyAcounts) {
		this.daCompanyAcounts = daCompanyAcounts;
	}

	public DaCompanyLogout getDaCompanyLogout() {
		return daCompanyLogout;
	}

	public void setDaCompanyLogout(DaCompanyLogout daCompanyLogout) {
		this.daCompanyLogout = daCompanyLogout;
	}

	public DaCompanyPass getDaCompanyPass() {
		return daCompanyPass;
	}

	public void setDaCompanyPass(DaCompanyPass daCompanyPass) {
		this.daCompanyPass = daCompanyPass;
	}

	public Set<DaIndustryParameter> getHzTradeTypes() {
		return hzTradeTypes;
	}

	public void setHzTradeTypes(Set<DaIndustryParameter> hzTradeTypes) {
		this.hzTradeTypes = hzTradeTypes;
	}

	public Long getFifthArea() {
		return fifthArea;
	}

	public void setFifthArea(Long fifthArea) {
		this.fifthArea = fifthArea;
	}

	public Long getFirstArea() {
		return firstArea;
	}

	public void setFirstArea(Long firstArea) {
		this.firstArea = firstArea;
	}

	public Long getFouthArea() {
		return fouthArea;
	}

	public void setFouthArea(Long fouthArea) {
		this.fouthArea = fouthArea;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrderProperty() {
		return orderProperty;
	}

	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}

	public boolean isOrderType() {
		return orderType;
	}

	public void setOrderType(boolean orderType) {
		this.orderType = orderType;
	}

	public String getTradeTypes() {
		return tradeTypes;
	}

	/**
	 * 设置企业的行业
	 * @param trades
	 */
	public void setTradeTypes(String trades) {
		tradeTypes = trades;
		if (trades != null && !"".equals(trades)) {
			for (String trade : trades.split(",")) {
				if (Long.parseLong(trade.trim()) != -1L) {
					boolean exists = false;
					for (DaIndustryParameter industry : hzTradeTypes) {
						if (industry.getId() == Long.parseLong(trade.trim())) {
							exists = true;
						}
					}
					if (!exists) {
						hzTradeTypes.add(new DaIndustryParameter(Long
								.parseLong(trade.trim())));
					}
				}
			}
		}
	}
	
	/**
	 * 设置分级分类参数
	 * @param points
	 */
	public void setPointTypes(String points) {
		if(points != null && !"".equals(points)) {
			for (String point : points.split(",")) {
				if(Long.parseLong(point.trim()) != -1L) {
					boolean exists = false;
					for (DaPointType pointType : daPointTypes) {
						if(pointType.getId() == Long.parseLong(point.trim())) {
							exists = true;
						}
					}
					if (!exists) {
						daPointTypes.add(new DaPointType(Long.parseLong(point.trim())));
					}
				}
			}
		}
	}

	public int getIsRepairCount() {
		return isRepairCount;
	}

	public void setIsRepairCount(int isRepairCount) {
		this.isRepairCount = isRepairCount;
	}

	public int getNotRepairCount() {
		return notRepairCount;
	}

	public void setNotRepairCount(int notRepairCount) {
		this.notRepairCount = notRepairCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<DaPointType> getDaPointTypes() {
		return daPointTypes;
	}

	public void setDaPointTypes(Set<DaPointType> daPointTypes) {
		this.daPointTypes = daPointTypes;
	}

	public String getCompanyLevel() {
		return companyLevel;
	}

	public void setCompanyLevel(String companyLevel) {
		this.companyLevel = companyLevel;
	}

	public Integer getCompanyPoint() {
		return companyPoint;
	}

	public void setCompanyPoint(Integer companyPoint) {
		this.companyPoint = companyPoint;
	}

	public Integer getAffrim() {
		return affrim;
	}

	public void setAffrim(Integer affrim) {
		this.affrim = affrim;
	}

	public Integer getOff() {
		return off;
	}

	public void setOff(Integer off) {
		this.off = off;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public Integer getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(Integer employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public Integer getFocusFireUnits() {
		return focusFireUnits;
	}

	public void setFocusFireUnits(Integer focusFireUnits) {
		this.focusFireUnits = focusFireUnits;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getProductionScale() {
		return productionScale;
	}

	public void setProductionScale(String productionScale) {
		this.productionScale = productionScale;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getEstablishmentDay() {
		return establishmentDay;
	}

	public void setEstablishmentDay(Date establishmentDay) {
		this.establishmentDay = establishmentDay;
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

	public String getTradeType1() {
		return tradeType1;
	}

	public void setTradeType1(String tradeType1) {
		this.tradeType1 = tradeType1;
	}

	public String getTradeType2() {
		return tradeType2;
	}

	public void setTradeType2(String tradeType2) {
		this.tradeType2 = tradeType2;
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

	public String getGovAdminOrg() {
		return govAdminOrg;
	}

	public void setGovAdminOrg(String govAdminOrg) {
		this.govAdminOrg = govAdminOrg;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Integer getIsModify() {
		return isModify;
	}

	public void setIsModify(Integer isModify) {
		this.isModify = isModify;
	}

	public String getHaveRegNum() {
		return haveRegNum;
	}

	public void setHaveRegNum(String haveRegNum) {
		this.haveRegNum = haveRegNum;
	}

	public Long getIsSYN() {
		return isSYN;
	}

	public void setIsSYN(Long isSYN) {
		this.isSYN = isSYN;
	}

	public String getStaticDate() {
		return staticDate;
	}

	public void setStaticDate(String staticDate) {
		this.staticDate = staticDate;
	}

	/**
	 * @return the isHighRiskWork
	 */
	public String getIsHighRiskWork() {
		return isHighRiskWork;
	}

	/**
	 * @param isHighRiskWork the isHighRiskWork to set
	 */
	public void setIsHighRiskWork(String isHighRiskWork) {
		this.isHighRiskWork = isHighRiskWork;
	}

	/**
	 * @return the highRiskWork
	 */
	public String getHighRiskWork() {
		return highRiskWork;
	}

	/**
	 * @param highRiskWork the highRiskWork to set
	 */
	public void setHighRiskWork(String highRiskWork) {
		this.highRiskWork = highRiskWork;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	public String getFirstIndustry() {
		Set<DaIndustryParameter> list = getHzTradeTypes();
		if (list != null && !list.isEmpty()) {
			for (DaIndustryParameter dip : list) {
				if (dip.getGradeRate() == 0) {
					return dip.getName();
				}
			}
		}
		return "";
	}
	
	public void buildByCompanyVo(CompanyVo companyVo) {
		this.regAddress = companyVo.getRegAddress();
		this.address = companyVo.getAddress();
		this.firstArea = companyVo.getFirstArea();
		this.secondArea = companyVo.getSecondArea();
		this.thirdArea = companyVo.getThirdArea();
		this.fouthArea = companyVo.getFouthArea();
		this.economicType1 = companyVo.getEconomicType1();
		this.economicType2 = companyVo.getEconomicType2();
		this.naEcoType1 = companyVo.getNaEcoType1();
		this.naEcoType2 = companyVo.getNaEcoType2();
		this.fddelegate = companyVo.getFddelegate();
		this.safetyMngPerson = companyVo.getSafetyMngPerson();
		this.productionScale = companyVo.getProductionScale();
		this.businessScope = companyVo.getBusinessScope();
	}
	
}
