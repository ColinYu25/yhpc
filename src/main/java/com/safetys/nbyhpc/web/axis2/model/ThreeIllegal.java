package com.safetys.nbyhpc.web.axis2.model;

import java.io.Serializable;

public class ThreeIllegal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8594085276132282205L;

	private Integer type;

	private Integer illegalBuildGetting;

	private Integer illegalBuildCandeled;

	private Integer illegalProduceGetting;

	private Integer illegalProduceCanceled;

	private Integer illegalTradeGetting;

	private Integer illegalTradeCalceled;

	public Integer getIllegalBuildCandeled() {
		return illegalBuildCandeled;
	}

	public void setIllegalBuildCandeled(Integer illegalBuildCandeled) {
		this.illegalBuildCandeled = illegalBuildCandeled;
	}

	public Integer getIllegalBuildGetting() {
		return illegalBuildGetting;
	}

	public void setIllegalBuildGetting(Integer illegalBuildGetting) {
		this.illegalBuildGetting = illegalBuildGetting;
	}

	public Integer getIllegalProduceCanceled() {
		return illegalProduceCanceled;
	}

	public void setIllegalProduceCanceled(Integer illegalProduceCanceled) {
		this.illegalProduceCanceled = illegalProduceCanceled;
	}

	public Integer getIllegalProduceGetting() {
		return illegalProduceGetting;
	}

	public void setIllegalProduceGetting(Integer illegalProduceGetting) {
		this.illegalProduceGetting = illegalProduceGetting;
	}

	public Integer getIllegalTradeCalceled() {
		return illegalTradeCalceled;
	}

	public void setIllegalTradeCalceled(Integer illegalTradeCalceled) {
		this.illegalTradeCalceled = illegalTradeCalceled;
	}

	public Integer getIllegalTradeGetting() {
		return illegalTradeGetting;
	}

	public void setIllegalTradeGetting(Integer illegalTradeGetting) {
		this.illegalTradeGetting = illegalTradeGetting;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public ThreeIllegal() {
	}

}
