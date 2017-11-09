package com.safetys.nbyhpc.domain.model;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.nbyhpc.domain.model.base.DaBaseModel;
import com.safetys.nbyhpc.util.SafetyTrouble;

public class ZjMineReport extends DaBaseModel {

	private static final long serialVersionUID = 1353663774839517768L;

	private String reportMonth = SafetyTrouble.getNowReportMonth();

	private String tel;

	private String fillMan;

	private String fillDate;

	private Integer type;

	private FkUser userId;

	private Integer state = 0;

	private String chargeMan;

	private Set<ZjMineReportDetail> zjMineReportDetails = new HashSet<ZjMineReportDetail>(0);

	private String year;
	
	private String month;
	
	private FkUser nowUser;
	
	private boolean queryReportMonth;
	
	private boolean isReport = false;
	
	private boolean isProvince = false;
	
	private boolean isCountry = false;
	
	private Long secondArea;//搜索地级市
	
	private boolean isPrint = false;//是否显示打印页
	
	private Integer backObstacle = 0;
	
	public ZjMineReport() {
	}
	
	public ZjMineReport(long id) {
		super(id);
	}
	
	/**
	 * 获取上报的中文显示
	 * @return
	 */
	public String getStateChinese() {
		if (state == SafetyTrouble.NO_REPORT) {
			return "未上报";
		} else if (state == SafetyTrouble.COUNTY_REPORT) {
			return "乡镇级上报";
		} else if (state == SafetyTrouble.CITY_REPORT) {
			return "县级上报";
		} else if (state == SafetyTrouble.PROVINCE_REPORT) {
			return "省级上报";
		}
		return "";
	}
	
	public boolean getAllowReport() {
		return (Boolean)SafetyTrouble.getState(nowUser, state).get("allow");
	}
	
	public boolean getAllowRollback() {
		return SafetyTrouble.allowBack(nowUser,state-backObstacle);
	}
	
	public String getReportMonth() {
		return this.reportMonth;
	}

	public void setReportMonth(String reportMonth) {
		this.reportMonth = reportMonth;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFillMan() {
		return this.fillMan;
	}

	public void setFillMan(String fillMan) {
		this.fillMan = fillMan;
	}

	public Integer getState() {
		return state;
	}

	public Date getFillDate() {
		DateFormat df = DateFormat.getDateInstance();
		Date fill = new Date();
		try {
			fill = df.parse(fillDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fill;
	}

	public void setFillDate(String fillDate) {
		this.fillDate = fillDate;
	}
	
	public void setFillDate(Date fillDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		this.fillDate = sdf.format(fillDate);
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getChargeMan() {
		return this.chargeMan;
	}

	public void setChargeMan(String chargeMan) {
		this.chargeMan = chargeMan;
	}

	public Set<ZjMineReportDetail> getZjMineReportDetails() {
		return zjMineReportDetails;
	}

	public void setZjMineReportDetails(Set<ZjMineReportDetail> zjMineReportDetails) {
		this.zjMineReportDetails = zjMineReportDetails;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public FkUser getUserId() {
		return userId;
	}

	public void setUserId(FkUser userId) {
		this.userId = userId;
	}

	public FkUser getNowUser() {
		return nowUser;
	}

	public void setNowUser(FkUser nowUser) {
		this.nowUser = nowUser;
	}

	public boolean isQueryReportMonth() {
		return queryReportMonth;
	}

	public void setQueryReportMonth(boolean queryReportMonth) {
		this.queryReportMonth = queryReportMonth;
	}

	public boolean isReport() {
		return isReport;
	}

	public void setReport(boolean isReport) {
		this.isReport = isReport;
	}

	public Long getSecondArea() {
		return secondArea;
	}

	public void setSecondArea(Long secondArea) {
		this.secondArea = secondArea;
	}

	public boolean isPrint() {
		return isPrint;
	}

	public void setPrint(boolean isPrint) {
		this.isPrint = isPrint;
	}

	public boolean isProvince() {
		return isProvince;
	}

	public void setProvince(boolean isProvince) {
		this.isProvince = isProvince;
	}

	public boolean isCountry() {
		return isCountry;
	}

	public void setCountry(boolean isCountry) {
		this.isCountry = isCountry;
	}

	public Integer getBackObstacle() {
		return backObstacle;
	}

	public void setBackObstacle(Integer backObstacle) {
		this.backObstacle = backObstacle;
	}
}
