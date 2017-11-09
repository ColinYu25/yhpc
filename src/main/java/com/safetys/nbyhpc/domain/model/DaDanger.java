package com.safetys.nbyhpc.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaCompany.java
 * @date 2009-08-08
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaDanger extends DaBaseModel {

	/**
	 * 重大隐患实体
	 */
	private static final long serialVersionUID = -3254977862997279298L;

	private DaCompanyPass daCompanyPass;

	private String dangerNo;

	private String dangerAdd;

	private Boolean govCoordination;

	private Boolean partStopProduct;

	private Boolean fullStopProduct;

	private Boolean target;

	private Boolean resource;

	private Boolean safetyMethod;

	private Boolean goods;

	private Date finishDate;

	private float governMoney;

	private Boolean emphasisProject;

	private Long userId;

	private Long firstArea;

	private Long secondArea;

	private Long fifthArea;

	private Long fouthArea;

	private Long thirdArea;

	private String description;

	private String linkMan;

	private String linkTel;

	private String linkMobile;

	private String chargePerson;

	private Date fillDate;

	private String fillMan;
	
	private int nowYear;
	
	private Date startTime;
	
	private Date endTime;

	private Set<DaDangerGorver> daDangerGorvers = new HashSet<DaDangerGorver>(0);

	private Set<DaRollcallCompany> daRollcallCompanies = new HashSet<DaRollcallCompany>(0);

	private Set<DaIndustryParameter> daIndustryParameters = new HashSet<DaIndustryParameter>(0);
	
	private String industryParameters;
	
	private String isGorver;
	
	private String isRollcall;
	
	private DaDangerGorver daDangerGorver;//lisl 列表显示计划治理和治理隐患
	
	private String isOver;//是否超期
	
	private Long hc;  //是否核查权限
    //添加以下三个属性  add by huangjl 2014-1-9
	
	//隐患来源编码
	private String hazardSourceCode;
	//隐患来源名称
	private String hazardSourceName;
	//隐患发现日期
	private Date  hazardHappenTime;
	//监管责任单位
	private String governDepartment;
	//监管责任人
	private String governPerson;
	//二级隐患编码
	private Set<DaIndustryParameter> secondDaIndustryParameters = new HashSet<DaIndustryParameter>(0);
	private String secondIndustryParameters;
	private float governMoney1;

	private float governMoney2;
	
	private Date deleteTime;
	
	private long flag;   //删除时是否已上报
	
	private int isSynchro;
	
	private int isAllYear;  //1 为年度统计  0 为季度统计
	
	private String jbFlag="0";//是否来自季报0表示不是来自季报，1表示来自季报
	
	private Integer jbYear;//季报年份
	
	private Integer jbQuarter;//季报季度
	
	private String repairState;//治理状态0：表示查询未治理的；1：表示查询治理的：2：表示查询全部
	
	public int getIsSynchro() {
		return isSynchro;
	}

	public void setIsSynchro(int isSynchro) {
		this.isSynchro = isSynchro;
	}

	public String getIsGorver() {
		return isGorver;
	}

	public void setIsGorver(String isGorver) {
		this.isGorver = isGorver;
	}

	public DaDanger() {
	}

	public DaDanger(long id) {
		super(id);
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public DaCompanyPass getDaCompanyPass() {
		return daCompanyPass;
	}

	public void setDaCompanyPass(DaCompanyPass daCompanyPass) {
		this.daCompanyPass = daCompanyPass;
	}

	public Set<DaDangerGorver> getDaDangerGorvers() {
		return daDangerGorvers;
	}

	public void setDaDangerGorvers(Set<DaDangerGorver> daDangerGorvers) {
		this.daDangerGorvers = daDangerGorvers;
	}


	public String getDangerAdd() {
		return dangerAdd;
	}

	public void setDangerAdd(String dangerAdd) {
		this.dangerAdd = dangerAdd;
	}

	public String getDangerNo() {
		return dangerNo;
	}

	public void setDangerNo(String dangerNo) {
		this.dangerNo = dangerNo;
	}

	public Set<DaRollcallCompany> getDaRollcallCompanies() {
		return daRollcallCompanies;
	}

	public void setDaRollcallCompanies(Set<DaRollcallCompany> daRollcallCompanies) {
		this.daRollcallCompanies = daRollcallCompanies;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEmphasisProject() {
		return emphasisProject;
	}

	public void setEmphasisProject(Boolean emphasisProject) {
		this.emphasisProject = emphasisProject;
	}

	public Long getFifthArea() {
		return fifthArea;
	}

	public void setFifthArea(Long fifthArea) {
		this.fifthArea = fifthArea;
	}

	public Date getFillDate() {
		return fillDate;
	}

	public void setFillDate(Date fillDate) {
		this.fillDate = fillDate;
	}

	public String getFillMan() {
		return fillMan;
	}

	public void setFillMan(String fillMan) {
		this.fillMan = fillMan;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
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

	public Boolean getFullStopProduct() {
		return fullStopProduct;
	}

	public void setFullStopProduct(Boolean fullStopProduct) {
		this.fullStopProduct = fullStopProduct;
	}

	public Boolean getGoods() {
		return goods;
	}

	public void setGoods(Boolean goods) {
		this.goods = goods;
	}

	public Boolean getGovCoordination() {
		return govCoordination;
	}

	public void setGovCoordination(Boolean govCoordination) {
		this.govCoordination = govCoordination;
	}

	public float getGovernMoney() {
		return governMoney;
	}

	public void setGovernMoney(float governMoney) {
		this.governMoney = governMoney;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
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

	public Boolean getPartStopProduct() {
		return partStopProduct;
	}

	public void setPartStopProduct(Boolean partStopProduct) {
		this.partStopProduct = partStopProduct;
	}

	public Boolean getResource() {
		return resource;
	}

	public void setResource(Boolean resource) {
		this.resource = resource;
	}

	public Boolean getSafetyMethod() {
		return safetyMethod;
	}

	public void setSafetyMethod(Boolean safetyMethod) {
		this.safetyMethod = safetyMethod;
	}

	public Long getSecondArea() {
		return secondArea;
	}

	public void setSecondArea(Long secondArea) {
		this.secondArea = secondArea;
	}

	public Boolean getTarget() {
		return target;
	}

	public void setTarget(Boolean target) {
		this.target = target;
	}

	public Long getThirdArea() {
		return thirdArea;
	}

	public void setThirdArea(Long thirdArea) {
		this.thirdArea = thirdArea;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIsRollcall() {
		return isRollcall;
	}

	public void setIsRollcall(String isRollcall) {
		this.isRollcall = isRollcall;
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

	public Set<DaIndustryParameter> getDaIndustryParameters() {
		return daIndustryParameters;
	}

	public void setDaIndustryParameters(
			Set<DaIndustryParameter> daIndustryParameters) {
		this.daIndustryParameters = daIndustryParameters;
	}
	
	/**
	 * 设置重大隐患类型
	 * @param trades
	 */
	public void setIndustryParameters(String trades) {
		industryParameters = trades;
		if (trades != null && !"".equals(trades)) {
			for (String trade : trades.split(",")) {
				if (Long.parseLong(trade.trim()) != -1L) {
					boolean exists = false;
					for (DaIndustryParameter industry : daIndustryParameters) {
						if (industry.getId() == Long.parseLong(trade.trim())) {
							exists = true;
						}
					}
					if (!exists) {
						daIndustryParameters.add(new DaIndustryParameter(Long.parseLong(trade.trim())));
					}
				}
			}
		}
	}
	
	/**
	 * 设置重大隐患二级类型
	 * @param trades
	 */
	public void setSecondIndustryParameters(String trades) {
		secondIndustryParameters = trades;
		if (trades != null && !"".equals(trades)) {
			for (String trade : trades.split(",")) {
				if (Long.parseLong(trade.trim()) != -1L) {
					boolean exists = false;
					for (DaIndustryParameter industry : secondDaIndustryParameters) {
						if (industry.getId() == Long.parseLong(trade.trim())) {
							exists = true;
						}
					}
					if (!exists) {
						secondDaIndustryParameters.add(new DaIndustryParameter(Long.parseLong(trade.trim())));
					}
				}
			}
		}
	}

	public String getIndustryParameters() {
		return industryParameters;
	}

	public int getNowYear() {
		return nowYear;
	}

	public void setNowYear(int nowYear) {
		this.nowYear = nowYear;
	}

	public DaDangerGorver getDaDangerGorver() {
		return daDangerGorver;
	}

	public void setDaDangerGorver(DaDangerGorver daDangerGorver) {
		this.daDangerGorver = daDangerGorver;
	}

	public String getIsOver() {
		return isOver;
	}

	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}

	public String getHazardSourceCode() {
		return hazardSourceCode;
	}

	public void setHazardSourceCode(String hazardSourceCode) {
		this.hazardSourceCode = hazardSourceCode;
	}

	public String getHazardSourceName() {
		return hazardSourceName;
	}

	public void setHazardSourceName(String hazardSourceName) {
		this.hazardSourceName = hazardSourceName;
	}

	public Set<DaIndustryParameter> getSecondDaIndustryParameters() {
		return secondDaIndustryParameters;
	}

	public void setSecondDaIndustryParameters(
			Set<DaIndustryParameter> secondDaIndustryParameters) {
		this.secondDaIndustryParameters = secondDaIndustryParameters;
	}

	public Date getHazardHappenTime() {
		return hazardHappenTime;
	}

	public void setHazardHappenTime(Date hazardHappenTime) {
		this.hazardHappenTime = hazardHappenTime;
	}

	public String getGovernDepartment() {
		return governDepartment;
	}

	public void setGovernDepartment(String governDepartment) {
		this.governDepartment = governDepartment;
	}

	public String getGovernPerson() {
		return governPerson;
	}

	public void setGovernPerson(String governPerson) {
		this.governPerson = governPerson;
	}

	public String getSecondIndustryParameters() {
		return secondIndustryParameters;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public long getFlag() {
		return flag;
	}

	public void setFlag(long flag) {
		this.flag = flag;
	}

	public Long getHc() {
		return hc;
	}

	public void setHc(Long hc) {
		this.hc = hc;
	}

	public float getGovernMoney1() {
		return governMoney1;
	}

	public void setGovernMoney1(float governMoney1) {
		this.governMoney1 = governMoney1;
	}

	public float getGovernMoney2() {
		return governMoney2;
	}

	public void setGovernMoney2(float governMoney2) {
		this.governMoney2 = governMoney2;
	}

	/**
	 * @return the jbFlag
	 */
	public String getJbFlag() {
		return jbFlag;
	}

	/**
	 * @param jbFlag the jbFlag to set
	 */
	public void setJbFlag(String jbFlag) {
		this.jbFlag = jbFlag;
	}

	/**
	 * @return the jbYear
	 */
	public Integer getJbYear() {
		return jbYear;
	}

	/**
	 * @param jbYear the jbYear to set
	 */
	public void setJbYear(Integer jbYear) {
		this.jbYear = jbYear;
	}

	/**
	 * @return the jbQuarter
	 */
	public Integer getJbQuarter() {
		return jbQuarter;
	}

	/**
	 * @param jbQuarter the jbQuarter to set
	 */
	public void setJbQuarter(Integer jbQuarter) {
		this.jbQuarter = jbQuarter;
	}

	/**
	 * @return the repairState
	 */
	public String getRepairState() {
		return repairState;
	}

	/**
	 * @param repairState the repairState to set
	 */
	public void setRepairState(String repairState) {
		this.repairState = repairState;
	}

	/**
	 * @return the isAllYear
	 */
	public int getIsAllYear() {
		return isAllYear;
	}

	/**
	 * @param isAllYear the isAllYear to set
	 */
	public void setIsAllYear(int isAllYear) {
		this.isAllYear = isAllYear;
	}


}
