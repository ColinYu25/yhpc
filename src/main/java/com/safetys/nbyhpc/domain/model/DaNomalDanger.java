package com.safetys.nbyhpc.domain.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;
import com.safetys.nbyhpc.util.DateUtils;

public class DaNomalDanger extends DaBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2676485783283145965L;

	private DaCompanyPass daCompanyPass;

	private List<DaNomalDanger> daNomalDangers;

	private Long companyPassId;

	private DaBag daBag;

	private Long bagId;

	private String linkMan;

	private String linkTel;

	private String linkMobile;

	private Integer type = 0;

	private DaIndustryParameter industry;

	private String description;

	private Date completedDate;

	private String completedDateString;

	private Long userId;

	private String industryName;

	private boolean danger;

	private boolean repaired;

	private int nowYear;

	private int repair;
	
	private Boolean fromApp = Boolean.FALSE; //false:默认隐患系统PC端 true:隐患手机端

	private String currentTime;// add by chy 2010-12-10 用来获取服务器当前的时间

	// 添加以下三个属性 add by huangjl 2014-1-9

	// 隐患来源编码
	private String hazardSourceCode;
	// 隐患来源名称
	private String hazardSourceName;
	// 二级隐患分类
	private Integer secondType = 0;
	
	private DaIndustryParameter secondIndustry;

	private float governMoney;

	private String sysFlag; // 同步标志

	private String sGovernMoney;
	
	private Integer isSynchro;// ，是否同步到中心库中标志

	private float governMoney1;

	private float governMoney2;
	
	private int yhcount; //隐患个数
	
	private int auto; //1 批量录入标志  
	
	private int inputType; //隐患录入方式   1  按明细  2 按个数
	
	private String flag;   //隐患批量录入 同一组标志

	private int isAllYear;  //1 为年度统计  0 为季度统计
	
	private String jbFlag="0";//是否来自季报0表示不是来自季报，1表示来自季报
	
	private Integer jbYear;//季报年份
	
	private Integer jbQuarter;//季报季度
	
	private String repairState;//治理状态0：表示查询未治理的；1：表示查询治理的：2：表示查询全部
	
	
	private Long companyId;//企业id
	
	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public int getRepair() {
		return repair;
	}

	public void setRepair(int repair) {
		this.repair = repair;
	}

	public DaCompanyPass getDaCompanyPass() {
		return daCompanyPass;
	}

	public void setDaCompanyPass(DaCompanyPass daCompanyPass) {
		this.daCompanyPass = daCompanyPass;
	}

	public DaBag getDaBag() {
		return daBag;
	}

	public void setDaBag(DaBag daBag) {
		this.daBag = daBag;
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

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCompletedDate() {
		// if (completedDateString != null && !"".equals(completedDateString)) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// try {
		// completedDate = sdf.parse(completedDateString);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }catch (Exception ex){
		// ex.printStackTrace();
		// }
		//
		// }
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCompanyPassId() {
		return companyPassId;
	}

	public void setCompanyPassId(Long companyPassId) {
		this.companyPassId = companyPassId;
	}

	public String getCompletedDateString() {
		if (completedDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			completedDateString = sdf.format(completedDate);
		}
		return completedDateString;
	}

	public void setCompletedDateString(String completedDateString) {
		this.completedDateString = completedDateString;
	}

	public Long getBagId() {
		return bagId;
	}

	public void setBagId(Long bagId) {
		this.bagId = bagId;
	}

	public String getCompletedDateCriteriaString() {
		if (completedDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(completedDate);
		}
		return null;
	}

	public DaIndustryParameter getIndustry() {
		return industry;
	}

	public void setIndustry(DaIndustryParameter industry) {
		this.industry = industry;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public boolean isDanger() {
		return danger;
	}

	public void setDanger(boolean danger) {
		this.danger = danger;
	}

	public int getNowYear() {
		return nowYear;
	}

	public void setNowYear(int nowYear) {
		this.nowYear = nowYear;
	}

	public boolean isRepaired() {
		return repaired;
	}

	public void setRepaired(boolean repaired) {
		this.repaired = repaired;
	}

	public List<DaNomalDanger> getDaNomalDangers() {
		return daNomalDangers;
	}

	public void setDaNomalDangers(List<DaNomalDanger> daNomalDangers) {
		this.daNomalDangers = daNomalDangers;
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

	public Integer getSecondType() {
		return secondType;
	}

	public void setSecondType(Integer secondType) {
		this.secondType = secondType;
	}

	public float getGovernMoney() {
		return governMoney;
	}

	public void setGovernMoney(float governMoney) {
		this.governMoney = governMoney;
	}

	public String getsGovernMoney() {
		sGovernMoney = String.valueOf(this.governMoney);
		return sGovernMoney;
	}

	public void setsGovernMoney(String sGovernMoney) {
		this.sGovernMoney = sGovernMoney;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public Integer getIsSynchro() {
		return isSynchro;
	}

	public void setIsSynchro(Integer isSynchro) {
		this.isSynchro = isSynchro;
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

	public int getYhcount() {
		return yhcount;
	}

	public void setYhcount(int yhcount) {
		this.yhcount = yhcount;
	}

	public int getInputType() {
		return inputType;
	}

	public void setInputType(int inputType) {
		this.inputType = inputType;
	}

	public int getAuto() {
		return auto;
	}

	public void setAuto(int auto) {
		this.auto = auto;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

	/**
	 * @return the companyId
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Boolean getFromApp() {
		if (fromApp == null) {
			return Boolean.FALSE;
		}
		return fromApp;
	}

	public void setFromApp(Boolean fromApp) {
		this.fromApp = fromApp;
	}

	public DaIndustryParameter getSecondIndustry() {
		return secondIndustry;
	}

	public void setSecondIndustry(DaIndustryParameter secondIndustry) {
		this.secondIndustry = secondIndustry;
	}

	/**
	 * 只有当月的数据可以修改
	 * @return
	 */
	public boolean isAllowEdit() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		return (String.valueOf(year) + String.valueOf(month)).equals(DateUtils.date2Str(getCreateTime(), "yyyyM"));
	}
	
}
