package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class Statistic  extends DaBaseModel  {
	
	/** 
	 * sql 数据实体
	 */
	private static final long serialVersionUID = -1653255062201106999L;

	private String companyName;
	
	private String safetyMngPerson;//安管负责人
	
	private String safetyMngPersonPhone;//安管负责人电话
	
	private String address;
	
	private String dangerNo;
	
	private String dangerAdd;
	
	private String linkMan;
	
	private String linkTel;
	
	private String  linkMobile;
	
	private String isRollcall;
	
	private String isGorver;
	
	private Long companyId;
	
	private Long thirdCode;
	
	private long areaCode;

	private String areaName;

	private String areaType;
	
	private String enumCode;

	private String enumName;
	
	private Integer inhere;//基数
	
	private Integer number;
	
	private Integer total;
	
	private Integer num;
	
	private Integer anum;
	
	private Integer bnum;
	
	private Integer cnum;
	
	private Integer dnum;
	
	private Integer aanum;
	
	private Integer bbnum;
	
	private Integer ccnum;
	
	private Integer ddnum;
	
	private Integer zero;
	
	private Integer qnum;
	
	private Integer sonCount;//用于页面控制
	
	private Integer sonNumber1;//用于页面控制
	
	private Integer sonNumber2;//用于页面控制
	
	private Integer industryId;
	
	private String name;
	
	private Integer aaanum;
	
	private Integer bbbnum;
	
	private Integer cccnum;
	
	private Integer dddnum;
	
	private Integer allCompanyNum;
	
	private Integer companyNum;

	private Integer troubNum;

	private Integer troubCleanNum;

	private Integer bigTroubNum;

	private Integer bigTroubCleanNum;

	private Integer planBigTroubNum;

	private Integer target;

	private Integer goods;

	private Integer resource;

	private Integer finishDate;

	private Integer safetyMethod;

	private Double governMoney;

	private Integer ddng5Num;
	
	private Integer proviceRcNum;
	
	private Integer cityRcNum;
	
	private Integer qtRcNum;
	
	private String yearMonth;
	
	private int year;
	
	private int quarter;
	
	private Integer type;
	
	private String remark;
	
	private String remark2;
	
	private String remark3;
	
	private int isAllYear;  //1 为年度统计  0 为季度统计
	
	private int isSonType; //1 为罗列所有子行业  0 为总行业
	
	private int isStType; // 统计类型：0 为季报进度  1 为隐患整治 2 为排查质量
	
	private int month;
	
	private int beg_month;
	
	private int end_month;
	
	private int reportedNum;//季报上报数量
	
	private Boolean isReport;//是否上报
	
	private String pipeNames;//企业管道名称集
	
	private String lineType;//管道类型
	
	private String dType;//隐患类型
	
	private String repaired;//隐患整改
	
	private Long secondArea;//详情二级区域
	
	private String paramType;//详情参数类型
	
	private String pipeName;//管道名称
	
	private String reportDate;//季报日期
	//add by huangjl
	
	private Double normalGovernMoney;
	
	private Boolean reFresh;//是否刷新
	
	private String bussnissAddress; 
	
	private Date setTime;
	
	private Long fatherId;//父节点id
	
	
	private Integer oldYear;//老年份
	private Integer oldQuarter;//老季度
	
	private Boolean needDaStatistic;
	
	public String getPipeName() {
		return pipeName;
	}

	public void setPipeName(String pipeName) {
		this.pipeName = pipeName;
	}

	public int getIsStType() {
		return isStType;
	}

	public void setIsStType(int isStType) {
		this.isStType = isStType;
	}

	public int getIsAllYear() {
		return isAllYear;
	}

	public void setIsAllYear(int isAllYear) {
		this.isAllYear = isAllYear;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getBigTroubCleanNum() {
		if (bigTroubCleanNum == null)
			return 0;
		return bigTroubCleanNum;
	}

	public void setBigTroubCleanNum(Integer bigTroubCleanNum) {
		this.bigTroubCleanNum = bigTroubCleanNum;
	}

	public Integer getBigTroubNum() {
		if (bigTroubNum == null)
			return 0;
		return bigTroubNum;
	}

	public void setBigTroubNum(Integer bigTroubNum) {
		this.bigTroubNum = bigTroubNum;
	}

	public Integer getCompanyNum() {
		if (companyNum == null)
			return 0;
		return companyNum;
	}

	public void setCompanyNum(Integer companyNum) {
		this.companyNum = companyNum;
	}

	public Integer getFinishDate() {
		if (finishDate == null)
			return 0;
		return finishDate;
	}

	public void setFinishDate(Integer finishDate) {
		this.finishDate = finishDate;
	}

	public Integer getGoods() {
		if (goods == null)
			return 0;
		return goods;
	}

	public void setGoods(Integer goods) {
		this.goods = goods;
	}

	public Double getGovernMoney() {
		if (governMoney == null)
			return 0.0;
		return governMoney;
	}

	public void setGovernMoney(Double governMoney) {
		this.governMoney = governMoney;
	}

	public Integer getPlanBigTroubNum() {
		if (planBigTroubNum == null)
			return 0;
		return planBigTroubNum;
	}

	public void setPlanBigTroubNum(Integer planBigTroubNum) {
		this.planBigTroubNum = planBigTroubNum;
	}

	public Integer getResource() {
		if (resource == null)
			return 0;
		return resource;
	}

	public void setResource(Integer resource) {
		this.resource = resource;
	}

	public Integer getSafetyMethod() {
		if (safetyMethod == null)
			return 0;
		return safetyMethod;
	}

	public void setSafetyMethod(Integer safetyMethod) {
		this.safetyMethod = safetyMethod;
	}

	public Integer getTarget() {
		if (target == null)
			return 0;
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public Integer getTroubCleanNum() {
		if (troubCleanNum == null)
			return 0;
		return troubCleanNum;
	}

	public void setTroubCleanNum(Integer troubCleanNum) {
		this.troubCleanNum = troubCleanNum;
	}

	public Integer getTroubNum() {
		if (troubNum == null)
			return 0;
		return troubNum;
	}

	public void setTroubNum(Integer troubNum) {
		this.troubNum = troubNum;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public  Statistic(){} 

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getIsRollcall() {
		return isRollcall;
	}

	public void setIsRollcall(String isRollcall) {
		this.isRollcall = isRollcall;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public Integer getAnum() {
		if (anum == null)
			return 0;
		return anum;
	}

	public void setAnum(Integer anum) {
		this.anum = anum;
	}

	public long getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(long areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getBnum() {
		if (bnum == null)
			return 0;
		return bnum;
	}

	public void setBnum(Integer bnum) {
		this.bnum = bnum;
	}

	public Integer getCnum() {
		if (cnum == null)
			return 0;
		return cnum;
	}

	public void setCnum(Integer cnum) {
		this.cnum = cnum;
	}

	public Integer getDnum() {
		if (dnum == null)
			return 0;
		return dnum;
	}

	public void setDnum(Integer dnum) {
		this.dnum = dnum;
	}

	public String getEnumCode() {
		return enumCode;
	}

	public void setEnumCode(String enumCode) {
		this.enumCode = enumCode;
	}

	public String getEnumName() {
		return enumName;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	public Integer getInhere() {
		if (inhere == null)
			return 0;
		return inhere;
	}

	public void setInhere(Integer inhere) {
		this.inhere = inhere;
	}

	public Integer getNum() {
		if (num == null)
			return 0;
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getTotal() {
		if (total == null)
			return 0;
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getAanum() {
		if (aanum == null)
			return 0;
		return aanum;
	}

	public void setAanum(Integer aanum) {
		this.aanum = aanum;
	}

	public Integer getBbnum() {
		if (bbnum == null)
			return 0;
		return bbnum;
	}

	public void setBbnum(Integer bbnum) {
		this.bbnum = bbnum;
	}

	public Integer getCcnum() {
		if (ccnum == null)
			return 0;
		return ccnum;
	}

	public void setCcnum(Integer ccnum) {
		this.ccnum = ccnum;
	}

	public Integer getDdnum() {
		if (ddnum == null)
			return 0;
		return ddnum;
	}

	public void setDdnum(Integer ddnum) {
		this.ddnum = ddnum;
	}

	public Integer getNumber() {
		if (number == null)
			return 0;
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getIndustryId() {
		return industryId;
	}

	public void setIndustryId(Integer industryId) {
		this.industryId = industryId;
	}

	public Integer getSonCount() {
		if (sonCount == null)
			return 0;
		return sonCount;
	}

	public void setSonCount(Integer sonCount) {
		this.sonCount = sonCount;
	}

	public Integer getSonNumber1() {
		if (sonNumber1 == null)
			return 0;
		return sonNumber1;
	}

	public void setSonNumber1(Integer sonNumber1) {
		this.sonNumber1 = sonNumber1;
	}

	public Integer getSonNumber2() {
		if (sonNumber2 == null)
			return 0;
		return sonNumber2;
	}

	public void setSonNumber2(Integer sonNumber2) {
		this.sonNumber2 = sonNumber2;
	}

	public Integer getAaanum() {
		if (aaanum == null)
			return 0;
		return aaanum;
	}

	public void setAaanum(Integer aaanum) {
		this.aaanum = aaanum;
	}

	public Integer getBbbnum() {
		if (bbbnum == null)
			return 0;
		return bbbnum;
	}

	public void setBbbnum(Integer bbbnum) {
		this.bbbnum = bbbnum;
	}

	public Integer getCccnum() {
		if (cccnum == null)
			return 0;
		return cccnum;
	}

	public void setCccnum(Integer cccnum) {
		this.cccnum = cccnum;
	}

	public Integer getDddnum() {
		if (dddnum == null)
			return 0;
		return dddnum;
	}

	public void setDddnum(Integer dddnum) {
		this.dddnum = dddnum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsGorver() {
		return isGorver;
	}

	public void setIsGorver(String isGorver) {
		this.isGorver = isGorver;
	}

	public Integer getZero() {
		if (zero == null)
			return 0;
		return zero;
	}

	public void setZero(Integer zero) {
		this.zero = zero;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public int getIsSonType() {
		return isSonType;
	}

	public void setIsSonType(int isSonType) {
		this.isSonType = isSonType;
	}

	public Integer getQnum() {
		return qnum;
	}

	public void setQnum(Integer qnum) {
		this.qnum = qnum;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getReportedNum() {
		return reportedNum;
	}

	public void setReportedNum(int reportedNum) {
		this.reportedNum = reportedNum;
	}

	public Long getThirdCode() {
		return thirdCode;
	}

	public void setThirdCode(Long thirdCode) {
		this.thirdCode = thirdCode;
	}

	public int getBeg_month() {
		return beg_month;
	}

	public void setBeg_month(int begMonth) {
		beg_month = begMonth;
	}

	public int getEnd_month() {
		return end_month;
	}

	public void setEnd_month(int endMonth) {
		end_month = endMonth;
	}

	public Integer getDdng5Num() {
		return ddng5Num;
	}

	public void setDdng5Num(Integer ddng5Num) {
		this.ddng5Num = ddng5Num;
	}

	public Integer getProviceRcNum() {
		return proviceRcNum;
	}

	public void setProviceRcNum(Integer proviceRcNum) {
		this.proviceRcNum = proviceRcNum;
	}

	public Integer getCityRcNum() {
		return cityRcNum;
	}

	public void setCityRcNum(Integer cityRcNum) {
		this.cityRcNum = cityRcNum;
	}

	public Boolean getIsReport() {
		return isReport;
	}

	public void setIsReport(Boolean isReport) {
		this.isReport = isReport;
	}

	public String getPipeNames() {
		return pipeNames;
	}

	public void setPipeNames(String pipeNames) {
		this.pipeNames = pipeNames;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public Long getSecondArea() {
		return secondArea;
	}

	public void setSecondArea(Long secondArea) {
		this.secondArea = secondArea;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public Integer getAllCompanyNum() {
		return allCompanyNum;
	}

	public void setAllCompanyNum(Integer allCompanyNum) {
		this.allCompanyNum = allCompanyNum;
	}

	public String getdType() {
		return dType;
	}

	public void setdType(String dType) { 
		this.dType = dType;
	}

	public String getRepaired() {
		return repaired;
	}

	public void setRepaired(String repaired) {
		this.repaired = repaired;
	}

	public Integer getQtRcNum() {
		return qtRcNum;
	}

	public void setQtRcNum(Integer qtRcNum) {
		this.qtRcNum = qtRcNum;
	}

	public Double getNormalGovernMoney() {
		return normalGovernMoney;
	}

	public void setNormalGovernMoney(Double normalGovernMoney) {
		this.normalGovernMoney = normalGovernMoney;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	/**
	 * @return the bussnissAddress
	 */
	public String getBussnissAddress() {
		return bussnissAddress;
	}

	/**
	 * @param bussnissAddress the bussnissAddress to set
	 */
	public void setBussnissAddress(String bussnissAddress) {
		this.bussnissAddress = bussnissAddress;
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

	public Boolean getReFresh() {
		return reFresh;
	}

	public void setReFresh(Boolean reFresh) {
		this.reFresh = reFresh;
	}

	public Date getSetTime() {
		return setTime;
	}

	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}

	/**
	 * @return the fatherId
	 */
	public Long getFatherId() {
		return fatherId;
	}

	/**
	 * @param fatherId the fatherId to set
	 */
	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}

	/**
	 * @return the oldYear
	 */
	public Integer getOldYear() {
		return oldYear;
	}

	/**
	 * @param oldYear the oldYear to set
	 */
	public void setOldYear(Integer oldYear) {
		this.oldYear = oldYear;
	}

	/**
	 * @return the oldQuarter
	 */
	public Integer getOldQuarter() {
		return oldQuarter;
	}

	/**
	 * @param oldQuarter the oldQuarter to set
	 */
	public void setOldQuarter(Integer oldQuarter) {
		this.oldQuarter = oldQuarter;
	}

	public Boolean getNeedDaStatistic() {
		return needDaStatistic;
	}

	public void setNeedDaStatistic(Boolean needDaStatistic) {
		this.needDaStatistic = needDaStatistic;
	}

}
