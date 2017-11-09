package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;

/**
 * @(#) SeasonReportFacadeIface.java
 * @date 2009-07-29
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public interface SeasonReportDetailFacadeIface {

	/**
	 * 创建一个行业信息
	 * 
	 * @param seasonReport
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Long createSeasonReport(DaIndustryParameter seasonReport)
			throws ApplicationAccessException;

	/**
	 * 加载一个行业信息
	 * 
	 * @param seasonReport
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaIndustryParameter loadSeasonReport(DaIndustryParameter seasonReport)
			throws ApplicationAccessException;

	/**
	 * 修改一个行业信息
	 * 
	 * @param seasonReport
	 * @throws ApplicationAccessException
	 */
	public void updateSeasonReport(DaIndustryParameter seasonReport)
			throws ApplicationAccessException;

	/**
	 * 删除行业信息(可批量)
	 * 
	 * @param seasonReport
	 * @throws ApplicationAccessException
	 */
	public void deleteSeasonReport(String ids) throws ApplicationAccessException;

	/**
	 * 加载行业信息，或分页或不分
	 * 
	 * @param seasonReport
	 * @param wrapper
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaIndustryParameter> loadSeasonReports(
			DaIndustryParameter seasonReport, UserDetailWrapper userDetail,
			Pagination pagination) throws ApplicationAccessException;

}
