/*
 * @(#) MaterialFacadeIface.java        
 * Date 2008-10-29                                           
 * Copyright (c) 2008 Safetys.cn.
 * All rights reserved.
 */
package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.MapMarker;


public interface MapFacadeIface {

	public FkArea loadArea(Long areaCode) throws ApplicationAccessException;
	
	public List<DaCompany> loadCompanys(DaCompany company, Pagination pagination, UserDetailWrapper userDetail)
			throws ApplicationAccessException;

	public MapMarker createMarker(MapMarker mapMarker, Long userId) throws ApplicationAccessException;

	public List<MapMarker> loadMapMarkers(Pagination pagination) throws ApplicationAccessException;
	
	public List<MapMarker> loadMapMarkers(List<DaCompany> companies, Pagination pagination) throws ApplicationAccessException;

	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException;

	public void deleteMarker(MapMarker mapMarker) throws ApplicationAccessException;

	public String loadPhMapMarkerByType(String markerType,Pagination pagination) throws ApplicationAccessException;

	public MapMarker loadMapMarker(String markerType, Long markerId,Pagination pagination) throws ApplicationAccessException;
	
	/**
	 * 
	 * 根据用户id查询用户信息
	 * @param userId userId
	 * @return 返回
	 * @throws Exception
	 */
	public List<DaCompany> loadCompaniesByUserId(UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	/**
	 * 根据用户是否拥有某个角色
	 * @param userDetail
	 * @param roleCode
	 * @return {@link Boolean}
	 * @throws ApplicationAccessException
	 */
	public boolean vlidateUserRoles(UserDetailWrapper userDetail,String roleCode)throws ApplicationAccessException;

	/**
	 * 根据用户id查询已通过的企业
	 * @param 
	 * @return 返回
	 * @throws Exception
	 */
	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException;

	/**
	 * 根据行业id查询行业类型信息
	 * @param 
	 * @return 返回
	 * @throws Exception
	 */
	public DaIndustryParameter loadTradeTypeById(DaIndustryParameter daIndustryParameter)throws ApplicationAccessException;

	/**
	 * 根据查询条件，显示地图上的标记
	 * @param company
	 * @param mapMarker
	 * @param userDetail
	 * @return List<MapMarker> 
	 * @throws Exception
	 */
	public List<MapMarker> loadMapMarkers(DaCompany company, MapMarker mapMarker);
	
	/**
	 * 
	 * 根据id查询单个区域信息
	 * @param 
	 * @return 返回
	 * @throws Exception
	 */
	public FkArea loadAreaById(Long id) throws ApplicationAccessException;

	/**
	 * 实现政府端地图快速定位后自动标注功能
	 * @param company
	 * @param pagination
	 * @param userDetail
	 * @return
	 */
	public List<DaCompany> loadCompanysForQuickSearch(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 查询企业库中未标注的企业
	 * @param company
	 * @param pagination
	 * @return
	 */
	public List<DaCompany> loadUnmarkerCompanies(List<DaCompany> companies,Pagination pagination) throws ApplicationAccessException;
	
	/**
	 * 打开地图时，默认搜索200家导入数据的企业.
	 * @param company
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaCompany> loadInitCompanys(DaCompany company, Pagination pagination, UserDetailWrapper userDetail)throws ApplicationAccessException;

	/**
	 * 导航选择规模以上10000家企业时
	 * @param company
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaCompany> loadMapForGsCompanies(DaCompany company, Pagination pagination, UserDetailWrapper userDetail)throws ApplicationAccessException;
}
