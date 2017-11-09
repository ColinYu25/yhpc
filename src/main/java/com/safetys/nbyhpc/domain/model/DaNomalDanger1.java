package com.safetys.nbyhpc.domain.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaNomalDanger1 extends DaBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2676485783283145965L;

	private DaCompanyPassHis daCompanyPassHis;
	
	private List<DaNomalDanger1> daNomalDangers;

	private Long companyPassId;

	private DaBag daBag;

	private Long bagId;

	private String linkMan;

	private String linkTel;

	private String linkMobile;

	private Integer type=0;
	
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
	
	private String currentTime;//add by chy 2010-12-10 用来获取服务器当前的时间
	
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

	public DaCompanyPassHis getDaCompanyPassHis() {
		return daCompanyPassHis;
	}

	public void setDaCompanyPassHis(DaCompanyPassHis daCompanyPassHis) {
		this.daCompanyPassHis = daCompanyPassHis;
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
//		if (completedDateString != null && !"".equals(completedDateString)) {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			try {
//				completedDate = sdf.parse(completedDateString);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}catch (Exception ex){
//				ex.printStackTrace();
//			}
//			
//		}
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
		if(completedDate!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			completedDateString=sdf.format(completedDate);
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
		if(completedDate!=null){
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

	public List<DaNomalDanger1> getDaNomalDangers() {
		return daNomalDangers;
	}

	public void setDaNomalDangers(List<DaNomalDanger1> daNomalDangers) {
		this.daNomalDangers = daNomalDangers;
	}

}
