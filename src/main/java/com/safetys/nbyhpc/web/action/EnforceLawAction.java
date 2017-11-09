package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaZhifaReport;
import com.safetys.nbyhpc.domain.model.DaZhifaReportDetail;
import com.safetys.nbyhpc.facade.iface.EnforceLawFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#) EnforceLawAction.java
 * @date 2010-06-07
 * @author wangjb
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class EnforceLawAction extends DaAppAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8913477851628248431L;

	private EnforceLawFacadeIface enforceLawFacadeIface;

	private List<DaIndustryParameter> daIndustryParameters;

	private DaZhifaReport daZhifaReport;

	private List<DaZhifaReportDetail> daZhifaReportDetails;

	private List<DaZhifaReport> daZhifaReports;

	private Pagination pagination;

	private List<FkArea> areas;

	public String createEnforceLawInit() {
		try {
			daIndustryParameters = enforceLawFacadeIface
					.loadTradeTypes(getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String createEnforceLaw() {
		try {
			daZhifaReport.setUserId(getUserId());
			enforceLawFacadeIface.createEnforceLaw(daZhifaReport,
					daZhifaReportDetails, getUserDetail());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String loadEnforceLaw() {
		try {
			createEnforceLawInit();
			daZhifaReport = enforceLawFacadeIface.loadEnforceLaw(daZhifaReport);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String loadEnforceLaws() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			areas = enforceLawFacadeIface.loadAreas(getUserDetail());
			daZhifaReports = enforceLawFacadeIface.loadEnforceLaws(
					getUserDetail(), pagination, daZhifaReport);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String updateEnforceLaw() {
		try {
			daZhifaReport.setUserId(getUserId());
			enforceLawFacadeIface.updateEnforceLaw(daZhifaReport,
					daZhifaReportDetails, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String deleteEnforceLaw() {
		try {
			enforceLawFacadeIface.deleteEnforceLaw(daZhifaReport);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String loadStatistics() {
		try {
			daZhifaReportDetails=enforceLawFacadeIface.loadStatistics(getUserDetail(),daZhifaReport);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setEnforceLawFacadeIface(
			EnforceLawFacadeIface enforceLawFacadeIface) {
		this.enforceLawFacadeIface = enforceLawFacadeIface;
	}

	public List<DaIndustryParameter> getDaIndustryParameters() {
		return daIndustryParameters;
	}

	public void setDaIndustryParameters(
			List<DaIndustryParameter> daIndustryParameters) {
		this.daIndustryParameters = daIndustryParameters;
	}

	public DaZhifaReport getDaZhifaReport() {
		return daZhifaReport;
	}

	public void setDaZhifaReport(DaZhifaReport daZhifaReport) {
		this.daZhifaReport = daZhifaReport;
	}

	public List<DaZhifaReportDetail> getDaZhifaReportDetails() {
		return daZhifaReportDetails;
	}

	public void setDaZhifaReportDetails(
			List<DaZhifaReportDetail> daZhifaReportDetails) {
		this.daZhifaReportDetails = daZhifaReportDetails;
	}

	public List<DaZhifaReport> getDaZhifaReports() {
		return daZhifaReports;
	}

	public void setDaZhifaReports(List<DaZhifaReport> daZhifaReports) {
		this.daZhifaReports = daZhifaReports;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<FkArea> getAreas() {
		return areas;
	}

	public void setAreas(List<FkArea> areas) {
		this.areas = areas;
	}

}
