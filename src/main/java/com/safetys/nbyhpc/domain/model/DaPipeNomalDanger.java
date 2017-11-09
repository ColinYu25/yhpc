package com.safetys.nbyhpc.domain.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * 管道一般隐患
 */
public class DaPipeNomalDanger extends DaBaseModel {

	
	private static final long serialVersionUID = -2676485783283145965L;

	private DaPipelineInfo pipeLine;
	
	private List<DaPipeNomalDanger> nomalDangers;

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
	
	private Long firstArea;

	private Long secondArea;

	private Long fifthArea;

	private Long fouthArea;

	private Long thirdArea;
	
	private String chargePerson;

	private Date fillDate;
	
	private String currentTime;//add by chy 2010-12-10 用来获取服务器当前的时间
	
	private boolean disable;
	
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

	public DaPipelineInfo getPipeLine() {
		return pipeLine;
	}

	public void setPipeLine(DaPipelineInfo pipeLine) {
		this.pipeLine = pipeLine;
	}

	public List<DaPipeNomalDanger> getNomalDangers() {
		return nomalDangers;
	}

	public void setNomalDangers(List<DaPipeNomalDanger> nomalDangers) {
		this.nomalDangers = nomalDangers;
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

	public Long getFifthArea() {
		return fifthArea;
	}

	public void setFifthArea(Long fifthArea) {
		this.fifthArea = fifthArea;
	}

	public Long getFouthArea() {
		return fouthArea;
	}

	public void setFouthArea(Long fouthArea) {
		this.fouthArea = fouthArea;
	}

	public Long getThirdArea() {
		return thirdArea;
	}

	public void setThirdArea(Long thirdArea) {
		this.thirdArea = thirdArea;
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public Date getFillDate() {
		return fillDate;
	}

	public void setFillDate(Date fillDate) {
		this.fillDate = fillDate;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}
	
}
