package com.safetys.nbyhpc.domain.model;

import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaItem.java
 * @date 2009-08-01
 * @author zhangx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaItem extends DaBaseModel {

	/**
	 * 工程项目实体
	 */
	private static final long serialVersionUID = -4361286867829233415L;

	private String itemname;

	private String itemaddress;

	private Long firstArea;

	private Long secondArea;

	private Long thirdArea;

	private Long fouthArea;

	private Long fifthArea;

	private String iscompleted;

	private String ownername;

	private String zcbName;

	private String zcbAdd;

	private String linkman;

	private String tel;

	private Long userId;

	private Integer type;
	
	private Boolean sp1;

	private Boolean sp2;

	private Boolean sp3;
	
	private Boolean sp4;
	
	private Long sp1_id;

	private Long sp2_id;

	private Long sp3_id;
	
	private Long sp4_id;
	
	private float buildArea;
	
	private Set<DaItemDanger> daItemDangers = new HashSet<DaItemDanger>(0);

	private Set<DaItemSeasonReport> daItemSeasonReports = new HashSet<DaItemSeasonReport>(0);
	
	private Integer unGoverDangernNum;//未治理隐患数
	
	private Integer goverDangernNum;//已治理隐患数
	
	private Integer self; // 1:本级 区域
	
	public DaItem(){
		
	}

	public DaItem(Long id) {
		super(id);
	}

	public String getItemname() {
		return this.itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getItemaddress() {
		return this.itemaddress;
	}

	public void setItemaddress(String itemaddress) {
		this.itemaddress = itemaddress;
	}

	

	public String getOwnername() {
		return this.ownername;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public Set getDaItemSeasonReports() {
		return this.daItemSeasonReports;
	}

	public void setDaItemSeasonReports(Set<DaItemSeasonReport> daItemSeasonReports) {
		this.daItemSeasonReports = daItemSeasonReports;
	}

	public String getZcbAdd() {
		return zcbAdd;
	}

	public void setZcbAdd(String zcbAdd) {
		this.zcbAdd = zcbAdd;
	}

	public String getZcbName() {
		return zcbName;
	}

	public void setZcbName(String zcbName) {
		this.zcbName = zcbName;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
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

	public String getIscompleted() {
		return iscompleted;
	}

	public void setIscompleted(String iscompleted) {
		this.iscompleted = iscompleted;
	}

	public Set<DaItemDanger> getDaItemDangers() {
		return daItemDangers;
	}

	public void setDaItemDangers(Set<DaItemDanger> daItemDangers) {
		this.daItemDangers = daItemDangers;
	}

	public Integer getGoverDangernNum() {
		return goverDangernNum;
	}

	public void setGoverDangernNum(Integer goverDangernNum) {
		this.goverDangernNum = goverDangernNum;
	}

	public Integer getUnGoverDangernNum() {
		return unGoverDangernNum;
	}

	public void setUnGoverDangernNum(Integer unGoverDangernNum) {
		this.unGoverDangernNum = unGoverDangernNum;
	}

	public float getBuildArea() {
		return buildArea;
	}

	public void setBuildArea(float buildArea) {
		this.buildArea = buildArea;
	}

	public Boolean getSp1() {
		return sp1;
	}

	public void setSp1(Boolean sp1) {
		this.sp1 = sp1;
	}

	public Boolean getSp2() {
		return sp2;
	}

	public void setSp2(Boolean sp2) {
		this.sp2 = sp2;
	}

	public Boolean getSp3() {
		return sp3;
	}

	public void setSp3(Boolean sp3) {
		this.sp3 = sp3;
	}

	public Boolean getSp4() {
		return sp4;
	}

	public void setSp4(Boolean sp4) {
		this.sp4 = sp4;
	}

	public Long getSp1_id() {
		return sp1_id;
	}

	public void setSp1_id(Long sp1_id) {
		this.sp1_id = sp1_id;
	}

	public Long getSp2_id() {
		return sp2_id;
	}

	public void setSp2_id(Long sp2_id) {
		this.sp2_id = sp2_id;
	}

	public Long getSp3_id() {
		return sp3_id;
	}

	public void setSp3_id(Long sp3_id) {
		this.sp3_id = sp3_id;
	}

	public Long getSp4_id() {
		return sp4_id;
	}

	public void setSp4_id(Long sp4_id) {
		this.sp4_id = sp4_id;
	}

	public Integer getSelf() {
		return self;
	}

	public void setSelf(Integer self) {
		this.self = self;
	}

}