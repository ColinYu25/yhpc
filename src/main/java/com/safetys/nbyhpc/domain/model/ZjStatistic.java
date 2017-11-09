package com.safetys.nbyhpc.domain.model;

public class ZjStatistic {

	private String mineOrOther;// 判断显示统计的是矿山还是其他

	private Integer type;// 行业类型

	private Integer bigTrouble = 0;// 重大隐患数

	private Integer bigTroubleGovern = 0;// 已整改销号数

	private Integer bigTroubleNotGovern = 0;// 未整改销号数

	private Integer targetTask = 0;// 落实治理目标任务

	private Integer goods = 0;// 落实治理经费物资

	private Integer worker = 0;// 落实治理机构人员

	private Integer governDate = 0;// 落实治理时间要求

	private Integer safetyMethod = 0;// 落实安全措施应急预案

	/**
	 * @depiction 新增报表中的统计栏目
	 * @author lih
	 * @since 2012-05-29
	 */
	private Integer wdw = 0;// 达到“五到位”要求的重大隐患

	private Integer guapaiTotal = 0;// 挂牌督办总数

	private Integer provinceGuapai = 0;// 省级挂牌督办

	private Integer cityGuapai = 0;// 市级挂牌督办

	private Integer countyGuapai = 0;// 县级挂牌督办

	/**
	 * 手写构造函数和方法
	 * 
	 */
	public ZjStatistic() {

	}

	/**
	 * 全参构造函数
	 * 
	 * @param type
	 * @param bigTrouble
	 * @param bigTroubleGovern
	 * @param targetTask
	 * @param goods
	 * @param worker
	 * @param governDate
	 * @param safetyMethod
	 */
	public ZjStatistic(Integer type, Integer bigTrouble, Integer bigTroubleGovern, Integer targetTask, Integer goods,
			Integer worker, Integer governDate, Integer safetyMethod) {
		this.type = type;
		this.bigTrouble = bigTrouble;
		this.bigTroubleGovern = bigTroubleGovern;
		this.targetTask = targetTask;
		this.goods = goods;
		this.worker = worker;
		this.governDate = governDate;
		this.safetyMethod = safetyMethod;
	}

	/**
	 * 累加
	 * 
	 * @param bigTrouble
	 * @param bigTroubleGovern
	 * @param targetTask
	 * @param goods
	 * @param worker
	 * @param governDate
	 * @param safetyMethod
	 * @param wdw
	 * @param guapaiTotal
	 * @param provinceGuapai
	 * @param cityGuapai
	 * @param countyGuapai
	 */
	public void addStatistic(Integer bigTrouble, Integer bigTroubleGovern, Integer targetTask, Integer goods,
			Integer worker, Integer governDate, Integer safetyMethod, Integer wdw, Integer guapaiTotal,
			Integer provinceGuapai, Integer cityGuapai, Integer countyGuapai) {
		this.bigTrouble += bigTrouble;
		this.bigTroubleGovern += bigTroubleGovern;
		this.targetTask += targetTask;
		this.goods += goods;
		this.worker += worker;
		this.governDate += governDate;
		this.safetyMethod += safetyMethod;
		this.wdw += wdw;
		this.guapaiTotal += guapaiTotal;
		this.provinceGuapai += provinceGuapai;
		this.cityGuapai += cityGuapai;
		this.countyGuapai += countyGuapai;
	}

	public void addStatistic(ZjStatistic statistic) {
		this.bigTrouble += statistic.getBigTrouble();
		this.bigTroubleGovern += statistic.getBigTroubleGovern();
		this.targetTask += statistic.getTargetTask();
		this.goods += statistic.getGoods();
		this.worker += statistic.getWorker();
		this.governDate += statistic.getGovernDate();
		this.safetyMethod += statistic.getSafetyMethod();
		this.wdw += statistic.getWdw();
		this.guapaiTotal += statistic.getGuapaiTotal();
		this.provinceGuapai += statistic.getProvinceGuapai();
		this.cityGuapai += statistic.getCityGuapai();
		this.countyGuapai += statistic.getCountyGuapai();
	}

	public void initStatistic() {
		this.bigTrouble = 0;
		this.bigTroubleGovern = 0;
		this.targetTask = 0;
		this.goods = 0;
		this.worker = 0;
		this.governDate = 0;
		this.safetyMethod = 0;
		this.wdw = 0;
		this.guapaiTotal = 0;
		this.provinceGuapai = 0;
		this.cityGuapai = 0;
		this.countyGuapai = 0;
	}

	/**
	 * getter and setter
	 * 
	 */

	public Integer getBigTrouble() {
		return bigTrouble;
	}

	public void setBigTrouble(Integer bigTrouble) {
		this.bigTrouble = bigTrouble;
	}

	public Integer getBigTroubleGovern() {
		return bigTroubleGovern;
	}

	public void setBigTroubleGovern(Integer bigTroubleGovern) {
		this.bigTroubleGovern = bigTroubleGovern;
	}

	public Integer getBigTroubleNotGovern() {
		bigTroubleNotGovern = bigTrouble - bigTroubleGovern;
		return bigTroubleNotGovern;
	}

	public void setBigTroubleNotGovern(Integer bigTroubleNotGovern) {
		this.bigTroubleNotGovern = bigTroubleNotGovern;
	}

	public boolean getBigTroubleIsDividend() {
		String quotient = String.valueOf(((double) bigTroubleGovern) / bigTrouble);
		boolean flag = (quotient.length() <= 4);
		return flag;
	}

	public Integer getGoods() {
		return goods;
	}

	public void setGoods(Integer goods) {
		this.goods = goods;
	}

	public Integer getGovernDate() {
		return governDate;
	}

	public void setGovernDate(Integer governDate) {
		this.governDate = governDate;
	}

	public String getMineOrOther() {
		return mineOrOther;
	}

	public void setMineOrOther(String mineOrOther) {
		this.mineOrOther = mineOrOther;
	}

	public Integer getSafetyMethod() {
		return safetyMethod;
	}

	public void setSafetyMethod(Integer safetyMethod) {
		this.safetyMethod = safetyMethod;
	}

	public Integer getTargetTask() {
		return targetTask;
	}

	public void setTargetTask(Integer targetTask) {
		this.targetTask = targetTask;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getWorker() {
		return worker;
	}

	public void setWorker(Integer worker) {
		this.worker = worker;
	}

	public Integer getWdw() {
		return wdw;
	}

	public void setWdw(Integer wdw) {
		this.wdw = wdw;
	}

	public Integer getGuapaiTotal() {
		return guapaiTotal;
	}

	public void setGuapaiTotal(Integer guapaiTotal) {
		this.guapaiTotal = guapaiTotal;
	}

	public Integer getProvinceGuapai() {
		return provinceGuapai;
	}

	public void setProvinceGuapai(Integer provinceGuapai) {
		this.provinceGuapai = provinceGuapai;
	}

	public Integer getCityGuapai() {
		return cityGuapai;
	}

	public void setCityGuapai(Integer cityGuapai) {
		this.cityGuapai = cityGuapai;
	}

	public Integer getCountyGuapai() {
		return countyGuapai;
	}

	public void setCountyGuapai(Integer countyGuapai) {
		this.countyGuapai = countyGuapai;
	}

}
