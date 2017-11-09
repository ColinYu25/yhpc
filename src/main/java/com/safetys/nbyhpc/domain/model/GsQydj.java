package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class GsQydj extends DaBaseModel {

	private static final long serialVersionUID = 914477115715012702L;

	private String zch;

	private String qymc;

	private String zs;

	private String fddbr;

	private Date rkrq;

	private Long userId;

	public GsQydj() {
	}

	public GsQydj(long id) {
		super(id);
	}

	public String getFddbr() {
		return fddbr;
	}

	public void setFddbr(String fddbr) {
		this.fddbr = fddbr;
	}

	public String getQymc() {
		return qymc;
	}

	public void setQymc(String qymc) {
		this.qymc = qymc;
	}

	public Date getRkrq() {
		return rkrq;
	}

	public void setRkrq(Date rkrq) {
		this.rkrq = rkrq;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getZch() {
		return zch;
	}

	public void setZch(String zch) {
		this.zch = zch;
	}

	public String getZs() {
		return zs;
	}

	public void setZs(String zs) {
		this.zs = zs;
	}

}
