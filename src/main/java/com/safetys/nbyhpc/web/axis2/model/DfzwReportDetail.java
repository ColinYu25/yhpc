/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.web.axis2.model;

import java.io.Serializable;

/**
 * 打非治违周报子表
 * 
 * author lih
 * 
 * @since 2012-5-9
 * @version 1.0.0
 */
public class DfzwReportDetail implements Serializable {

	private static final long serialVersionUID = -5003193015650463596L;

	private Integer wuzheng;

	private Integer guanbi;

	private Integer tingchan;

	private Integer manbao;

	private Integer jubuzhixing;

	private Integer feifayonggong;

	private Integer zuoyeguicheng;

	private Integer gongyi;

	private Integer zhidubujianquan;

	private Integer zhongda;

	private Integer yingji;

	private Integer xincailiao;

	private Integer qita;

	private String type;

	public DfzwReportDetail() {
	}
	
	public DfzwReportDetail(String type, Integer wuzheng, Integer guanbi, Integer tingchan, Integer manbao,
			Integer jubuzhixing, Integer feifayonggong, Integer zuoyeguicheng, Integer gongyi, Integer zhidubujianquan,
			Integer zhongda, Integer yingji, Integer xincailiao, Integer qita) {
		this.wuzheng = wuzheng;
		this.guanbi = guanbi;
		this.tingchan = tingchan;
		this.manbao = manbao;
		this.jubuzhixing = jubuzhixing;
		this.feifayonggong = feifayonggong;
		this.zuoyeguicheng = zuoyeguicheng;
		this.gongyi = gongyi;
		this.zhidubujianquan = zhidubujianquan;
		this.zhongda = zhongda;
		this.yingji = yingji;
		this.xincailiao = xincailiao;
		this.qita = qita;
		this.type = type;
	}

	public Integer getWuzheng() {
		return wuzheng;
	}

	public void setWuzheng(Integer wuzheng) {
		this.wuzheng = wuzheng;
	}

	public Integer getGuanbi() {
		return guanbi;
	}

	public void setGuanbi(Integer guanbi) {
		this.guanbi = guanbi;
	}

	public Integer getTingchan() {
		return tingchan;
	}

	public void setTingchan(Integer tingchan) {
		this.tingchan = tingchan;
	}

	public Integer getManbao() {
		return manbao;
	}

	public void setManbao(Integer manbao) {
		this.manbao = manbao;
	}

	public Integer getJubuzhixing() {
		return jubuzhixing;
	}

	public void setJubuzhixing(Integer jubuzhixing) {
		this.jubuzhixing = jubuzhixing;
	}

	public Integer getFeifayonggong() {
		return feifayonggong;
	}

	public void setFeifayonggong(Integer feifayonggong) {
		this.feifayonggong = feifayonggong;
	}

	public Integer getZuoyeguicheng() {
		return zuoyeguicheng;
	}

	public void setZuoyeguicheng(Integer zuoyeguicheng) {
		this.zuoyeguicheng = zuoyeguicheng;
	}

	public Integer getGongyi() {
		return gongyi;
	}

	public void setGongyi(Integer gongyi) {
		this.gongyi = gongyi;
	}

	public Integer getZhidubujianquan() {
		return zhidubujianquan;
	}

	public void setZhidubujianquan(Integer zhidubujianquan) {
		this.zhidubujianquan = zhidubujianquan;
	}

	public Integer getZhongda() {
		return zhongda;
	}

	public void setZhongda(Integer zhongda) {
		this.zhongda = zhongda;
	}

	public Integer getYingji() {
		return yingji;
	}

	public void setYingji(Integer yingji) {
		this.yingji = yingji;
	}

	public Integer getXincailiao() {
		return xincailiao;
	}

	public void setXincailiao(Integer xincailiao) {
		this.xincailiao = xincailiao;
	}

	public Integer getQita() {
		return qita;
	}

	public void setQita(Integer qita) {
		this.qita = qita;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
