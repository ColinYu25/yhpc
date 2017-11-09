package com.safetys.nbyhpc.domain.model;


// Generated 2011-1-8 11:07:39 by Hibernate Tools 3.2.0.b9


/**
 * 燃气管道信息实体
 */
public class DaRqPipelineInfo extends DaPipelineInfo {
	
	private double pressure2;//设计压力
	
	private double maxPressure2;//最高压力
	
	private double diameter2;//管径

	private double deep2;//壁厚
	
	private Integer ghxklx;//是否办理规划许可 ， 全部办理、部分办理、没有办理
	
	private Integer jgys;//是否竣工验收 ， 全部验收、部分验收、未通过
	
	private Integer ylgdsydj;//是否妥协压力管道使用登记验收： 全部办理、部分办理、没有办理
	
	private Boolean hasJyxkq;//是否取得管道燃气特许经营许可权
	
	private Long areaCode;//所在区域
	
	
	public double getDeep2() {
		return deep2;
	}

	public void setDeep2(double deep2) {
		this.deep2 = deep2;
	}

	public double getDiameter2() {
		return diameter2;
	}

	public void setDiameter2(double diameter2) {
		this.diameter2 = diameter2;
	}

	public double getMaxPressure2() {
		return maxPressure2;
	}

	public void setMaxPressure2(double maxPressure2) {
		this.maxPressure2 = maxPressure2;
	}

	public double getPressure2() {
		return pressure2;
	}

	public void setPressure2(double pressure2) {
		this.pressure2 = pressure2;
	}

	public Integer getGhxklx() {
		return ghxklx;
	}

	public void setGhxklx(Integer ghxklx) {
		this.ghxklx = ghxklx;
	}

	public Boolean getHasJyxkq() {
		return hasJyxkq;
	}

	public void setHasJyxkq(Boolean hasJyxkq) {
		this.hasJyxkq = hasJyxkq;
	}

	public Integer getJgys() {
		return jgys;
	}

	public void setJgys(Integer jgys) {
		this.jgys = jgys;
	}

	public Integer getYlgdsydj() {
		return ylgdsydj;
	}

	public void setYlgdsydj(Integer ylgdsydj) {
		this.ylgdsydj = ylgdsydj;
	}

	public Long getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Long areaCode) {
		this.areaCode = areaCode;
	}

}
