package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaSeasonReportOther;
import com.safetys.nbyhpc.domain.model.Statistic;

/**
 * @(#) SeasonReportOtherFacadeIface.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public interface SeasonReportOtherFacadeIface {

	/**
	 * 创建一个打非信息
	 * 
	 * @param seasonReportOther
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void createSeasonReportOther(DaSeasonReportOther seasonReportOther)
			throws ApplicationAccessException;

	/**
	 * 加载一个打非信息
	 * 
	 * @param seasonReportOther
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaSeasonReportOther loadSeasonReportOther(DaSeasonReportOther seasonReportOther)
			throws ApplicationAccessException;

	/**
	 * 修改一个打非信息
	 * 
	 * @param seasonReportOther
	 * @throws ApplicationAccessException
	 */
	public void updateSeasonReportOther(DaSeasonReportOther seasonReportOther)
			throws ApplicationAccessException;

	/**
	 * 删除打非信息(可批量)
	 * 
	 * @param seasonReportOther
	 * @throws ApplicationAccessException
	 */
	public void deleteSeasonReportOthers(String ids)
			throws ApplicationAccessException;

	public void deleteSeasonReportOther(DaSeasonReportOther seasonReportOther)
			throws ApplicationAccessException;

	/**
	 * 加载打非信息，或分页或不分
	 * 
	 * @param seasonReportOther
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaSeasonReportOther> loadSeasonReportOthers(DaSeasonReportOther seasonReportOther,
			Pagination pagination, UserDetailWrapper userDetail,DaCompany company)
			throws ApplicationAccessException;
	
	public List<Statistic> loadSeasonReportOtherStatistic(UserDetailWrapper userDetail)throws ApplicationAccessException;
	
	public List<Statistic> loadSeasonReportOtherAreaStatistic(UserDetailWrapper userDetail)throws ApplicationAccessException;
	
	public List loadHiddenGovernanceReportStatistic(DaSeasonReportOther seasonReportOther)throws ApplicationAccessException;
	
	public List loadHiddenKeyIndustriesReportStatistic(DaSeasonReportOther seasonReportOther)throws ApplicationAccessException;
	
	public List<DaSeasonReportOther> loadSeasonReportOtherList(DaSeasonReportOther seasonReportOther,
			Pagination pagination, UserDetailWrapper userDetail,DaCompany company)
			throws ApplicationAccessException;

	/**
	 * 加载行业信息
	 * 
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaIndustryParameter> loadIndustryParameters(
			UserDetailWrapper userDetail) throws ApplicationAccessException;
}
