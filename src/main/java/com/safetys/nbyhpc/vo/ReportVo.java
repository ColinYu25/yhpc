package com.safetys.nbyhpc.vo;

import java.io.Serializable;

public class ReportVo implements Serializable {

	private static final long serialVersionUID = -2614436081497648732L;

	private String firstArea;
	
	private String secondArea;
	
	private String thirdArea;

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
	
}
