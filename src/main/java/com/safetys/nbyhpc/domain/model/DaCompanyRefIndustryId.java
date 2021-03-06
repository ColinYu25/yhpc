package com.safetys.nbyhpc.domain.model;

// Generated 2009-7-31 18:24:16 by Hibernate Tools 3.2.0.b9

/**
 * DaCompanyRefIndustryId generated by hbm2java
 */
public class DaCompanyRefIndustryId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2854959933918089661L;

	private long parDaComId;

	private long parDaIndId;

	public DaCompanyRefIndustryId() {
	}

	public DaCompanyRefIndustryId(long parDaComId, long parDaIndId) {
		this.parDaComId = parDaComId;
		this.parDaIndId = parDaIndId;
	}

	public long getParDaComId() {
		return this.parDaComId;
	}

	public void setParDaComId(long parDaComId) {
		this.parDaComId = parDaComId;
	}

	public long getParDaIndId() {
		return this.parDaIndId;
	}

	public void setParDaIndId(long parDaIndId) {
		this.parDaIndId = parDaIndId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DaCompanyRefIndustryId))
			return false;
		DaCompanyRefIndustryId castOther = (DaCompanyRefIndustryId) other;

		return (this.getParDaComId() == castOther.getParDaComId())
				&& (this.getParDaIndId() == castOther.getParDaIndId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getParDaComId();
		result = 37 * result + (int) this.getParDaIndId();
		return result;
	}

}
