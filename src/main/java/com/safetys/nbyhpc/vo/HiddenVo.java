package com.safetys.nbyhpc.vo;

/**
 * 隐患汇总查询
 * @author yangb
 *
 */
public class HiddenVo extends ReportVo {

	private static final long serialVersionUID = 8054248830420152406L;

	private String description;
	
	private String year; //隐患发现年份
	
	private String hiddenLevel; //隐患等级
	
	private String type1;//隐患分类1
	
	private String type2;//隐患分类2
	
	private Boolean completed; //是否完成
	
	private String fromSys; //从哪个系统过来
	
	private String companyName;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getHiddenLevel() {
		return hiddenLevel;
	}

	public void setHiddenLevel(String hiddenLevel) {
		this.hiddenLevel = hiddenLevel;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public String getFromSys() {
		return fromSys;
	}

	public void setFromSys(String fromSys) {
		this.fromSys = fromSys;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
