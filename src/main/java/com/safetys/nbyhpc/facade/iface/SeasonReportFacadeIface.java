package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaBag;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaSeasonReport;
import com.safetys.nbyhpc.domain.model.DaSeasonReportDetail;

/**
 * @(#) SeasonReportFacadeIface.java
 * @date 2009-07-29
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public interface SeasonReportFacadeIface {

	/**
	 * 加载所有季报行业信息
	 * 
	 * @return
	 * @throws ApplicationAccessException
	 */

	public List<DaIndustryParameter> loadTradeTypesForSeasonReport()
			throws ApplicationAccessException;

	/**
	 * 创建一个行业信息
	 * 
	 * @param seasonReport
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Long createSeasonReport(DaSeasonReport seasonReport)
			throws ApplicationAccessException;

	public void createSeasonReport(DaSeasonReport seasonReport,
			List<DaSeasonReportDetail> daSeasonReportDetailsl)
			throws ApplicationAccessException;

	/**
	 * 加载一个行业信息
	 * 
	 * @param seasonReport
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaSeasonReport loadSeasonReport(DaSeasonReport seasonReport)
			throws ApplicationAccessException;

	public DaSeasonReport loadSeasonReport(DaCompany company, int year,
			int seasonNumber) throws ApplicationAccessException;

	public DaSeasonReport loadSeasonReport(DaBag bag, int year, int seasonNumber)
			throws ApplicationAccessException;

	/**
	 * 修改一个行业信息
	 * 
	 * @param seasonReport
	 * @throws ApplicationAccessException
	 */
	public void updateSeasonReport(DaSeasonReport seasonReport)
			throws ApplicationAccessException;

	/**
	 * 删除行业信息(可批量)
	 * 
	 * @param seasonReport
	 * @throws ApplicationAccessException
	 */
	public void deleteSeasonReport(String ids)
			throws ApplicationAccessException;

	/**
	 * 加载行业信息，或分页或不分
	 * 
	 * @param seasonReport
	 * @param wrapper
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaSeasonReport> loadSeasonReports(DaSeasonReport seasonReport,
			UserDetailWrapper userDetail, Pagination pagination)
			throws ApplicationAccessException;

	public List<DaSeasonReport> loadSeasonReports(Integer[] compIds,
			UserDetailWrapper userDetail, Pagination pagination)
			throws ApplicationAccessException;

	public List<DaSeasonReport> loadSeasonReportsIsGorver(
			DaSeasonReport seasonReport, UserDetailWrapper userDetail,
			Pagination pagination) throws ApplicationAccessException;

	/**
	 * 加载已确认企业集合和季报的存在状态
	 * 
	 * @param companise
	 */
	// public List<DaCompany> companiseAffirmToSeasonIsExist(DaCompany company,
	// Pagination pagination, int year, int quarter,
	// UserDetailWrapper userDetail, int isQuarter)
	// throws ApplicationAccessException;
	public List<DaCompany> companiseAffirmToSeasonIsExist(DaCompany company,
			Pagination pagination, UserDetailWrapper userDetail)
			throws ApplicationAccessException;

	public List<DaCompanyPass> companiesByAffirm(DaCompany company,
			Pagination pagination, UserDetailWrapper userDetail)
			throws ApplicationAccessException;

	public List<DaBag> bagsToSeasonIsExist(DaBag bag, Pagination pagination,
			int year, int quarter, UserDetailWrapper userDetail, int isQuarter)
			throws ApplicationAccessException;

}
