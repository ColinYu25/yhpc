package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;
import com.safetys.nbyhpc.util.HiddenLevel;
import com.safetys.nbyhpc.util.HiddenSource;

/**
 * 隐患汇总表
 * @author yangb
 *
 */
public class HiddenSummary extends DaBaseModel {

	private static final long serialVersionUID = 3737674658333924814L;

	private Date hiddenDate; //隐患发现时间
	
	private String description; //隐患描述
	
	private boolean completed; //是否整改完成
	
	private Date completeDate; //整改完成日期
	
	private String hiddenLevel; //隐患等级:一般隐患 重大隐患
	
	/**
	 *  暂时没有用到这个字段
	 */
	private String hiddenSource; //隐患来源
	
	private String type1; //隐患分类1
	
	private String type2; //隐患分类2
	
	private String companyName; //企业名称
	
	private Long companyId; //中心库企业ID
	
	private String uuid; //中心库企业uuid
	
	private String fromSys; //数据来自哪个系统
	
	private String fromTable;//来自中心库的哪个表
	
	private String fromId; //中心库fromTable表数据ID
	
	//数据库里区域 默认值 为0
	private String firstArea;
	
	private String secondArea;
	
	private String thirdArea;
	
	private String fourthArea;
	
	private String fifthArea;
	
	private String temp1; //当sys =xzzf 存的是现场检查过记录ID
	private String temp2; //当temp1有值时候，存的是执法隐患相同现场检查记录的json
	
	//不参加持久化
	private String type1Text;
	private String type2Text;

	public Date getHiddenDate() {
		return hiddenDate;
	}

	public void setHiddenDate(Date hiddenDate) {
		this.hiddenDate = hiddenDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getHiddenLevel() {
		return hiddenLevel;
	}

	public void setHiddenLevel(String hiddenLevel) {
		this.hiddenLevel = hiddenLevel;
	}

	public String getHiddenSource() {
		return hiddenSource;
	}

	public void setHiddenSource(String hiddenSource) {
		this.hiddenSource = hiddenSource;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFromSys() {
		return fromSys;
	}

	public void setFromSys(String fromSys) {
		this.fromSys = fromSys;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getFromTable() {
		return fromTable;
	}

	public void setFromTable(String fromTable) {
		this.fromTable = fromTable;
	}

	public String getFirstArea() {
		return firstArea;
	}

	public void setFirstArea(String firstArea) {
		this.firstArea = firstArea;
	}

	public String getSecondArea() {
		return secondArea;
	}

	public void setSecondArea(String secondArea) {
		this.secondArea = secondArea;
	}

	public String getThirdArea() {
		return thirdArea;
	}

	public void setThirdArea(String thirdArea) {
		this.thirdArea = thirdArea;
	}

	public String getFourthArea() {
		return fourthArea;
	}

	public void setFourthArea(String fourthArea) {
		this.fourthArea = fourthArea;
	}

	public String getFifthArea() {
		return fifthArea;
	}

	public void setFifthArea(String fifthArea) {
		this.fifthArea = fifthArea;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTemp1() {
		return temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public String getTemp2() {
		return temp2;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public String getType1Text() {
		return type1Text;
	}

	public void setType1Text(String type1Text) {
		this.type1Text = type1Text;
	}

	public String getType2Text() {
		return type2Text;
	}

	public void setType2Text(String type2Text) {
		this.type2Text = type2Text;
	}

	public String getHiddenLevelText() {
		if (!StringUtils.isBlank(hiddenLevel)) {
			return HiddenLevel.valueOf(hiddenLevel).getText();
		}
		return null;
	}
	
	public String getFromSysText() {
		if (!StringUtils.isBlank(fromSys)) {
			return HiddenSource.valueOf(fromSys).getText();
		}
		return null;
	}
	
	public JSONArray getTemp2JsonArray() {
		if (StringUtils.isNotBlank(temp2)) {
			return JSONArray.fromObject(temp2);
		}
		return new JSONArray();
	}
}
