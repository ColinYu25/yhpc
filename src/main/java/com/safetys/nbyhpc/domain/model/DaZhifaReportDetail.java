package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaZhifaReportDetail extends DaBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2054279843860223065L;

	private DaZhifaReport daZhifaReport;
	
	private int data1;

	private int data2;

	private int data3;

	private int data4;

	private int data5;

	private int data6;

	private int data7;

	private int data8;

	private int data9;

	private int data10;

	private int total;
	
	private Long userId;
	
	private Long industryParameterId;
	
	private String Name;//统计参数：行业名称
	

	public Long getIndustryParameterId() {
		return industryParameterId;
	}

	public void setIndustryParameterId(Long industryParameterId) {
		this.industryParameterId = industryParameterId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getData1() {
		return data1;
	}

	public void setData1(int data1) {
		this.data1 = data1;
	}

	public int getData10() {
		return data10;
	}

	public void setData10(int data10) {
		this.data10 = data10;
	}

	public int getData2() {
		return data2;
	}

	public void setData2(int data2) {
		this.data2 = data2;
	}

	public int getData3() {
		return data3;
	}

	public void setData3(int data3) {
		this.data3 = data3;
	}

	public int getData4() {
		return data4;
	}

	public void setData4(int data4) {
		this.data4 = data4;
	}

	public int getData5() {
		return data5;
	}

	public void setData5(int data5) {
		this.data5 = data5;
	}

	public int getData6() {
		return data6;
	}

	public void setData6(int data6) {
		this.data6 = data6;
	}

	public int getData7() {
		return data7;
	}

	public void setData7(int data7) {
		this.data7 = data7;
	}

	public int getData8() {
		return data8;
	}

	public void setData8(int data8) {
		this.data8 = data8;
	}

	public int getData9() {
		return data9;
	}

	public void setData9(int data9) {
		this.data9 = data9;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public DaZhifaReport getDaZhifaReport() {
		return daZhifaReport;
	}

	public void setDaZhifaReport(DaZhifaReport daZhifaReport) {
		this.daZhifaReport = daZhifaReport;
	}
	public boolean equals(Object obj) {
	    if (obj == null || !(obj instanceof DaZhifaReportDetail)) {
	      return false;
	    }
	    DaZhifaReportDetail o = (DaZhifaReportDetail) obj;
	    return this.industryParameterId == o.industryParameterId && this.industryParameterId.equals(o.industryParameterId);
	  }
	  public int hashCode() {
	    int hashCode = industryParameterId.hashCode();
	    return hashCode;
	  }

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
}
