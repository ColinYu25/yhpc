package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallCompany;

/**
 * @(#) RollcallCompanyFacadeIface.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public interface PipeRollcallCompanyFacadeIface {

	/**
	 * 创建一个隐患挂牌信息
	 * 
	 * @param rollcallCompany
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Long createRollcallCompany(DaPipeRollcallCompany rollcallCompany)
			throws ApplicationAccessException;

	/**
	 * 加载一个隐患挂牌信息
	 * 
	 * @param rollcallCompany
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaPipeRollcallCompany loadRollcallCompany(DaPipeRollcallCompany rollcallCompany)
			throws ApplicationAccessException;

	public DaPipeDanger loadDanger(DaPipeDanger danger) throws ApplicationAccessException;
	
	public DaPipelineCompanyInfo loadCompany(DaPipelineCompanyInfo pipeLine) throws ApplicationAccessException;
	
	
	/**
	 * 修改一个隐患挂牌信息
	 * 
	 * @param rollcallCompany
	 * @throws ApplicationAccessException
	 */
	public void updateRollcallCompany(DaPipeRollcallCompany rollcallCompany)
			throws ApplicationAccessException;

	public void updateRollcallCompanyForNotice(DaPipeRollcallCompany rollcallCompany)
	throws ApplicationAccessException;
	
	/**
	 * 加载隐患挂牌信息
	 * 
	 * @param rollcallCompany
	 * @throws ApplicationAccessException
	 */
	public List<DaPipeRollcallCompany> loadRollcallCompanies(Long dangerId)
			throws ApplicationAccessException;

	/**
	 * 隐患挂牌分级列表
	 * 
	 * @param rollcallCompany
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaPipeRollcallCompany> loadRollcallCompaniesOfLevel(
			DaPipeRollcallCompany rollcallCompany,DaCompany company,DaPipelineCompanyInfo pipeLine, Pagination pagination,
			UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	public List<DaPipeRollcallCompany> loadRollcallCompaniesOfLevel(DaPipelineCompanyInfo pipeLine,
			Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 取消挂牌
	 * 
	 * @param id
	 * @throws ApplicationAccessException
	 */
	public void deleteRollcallCompany(String id) throws ApplicationAccessException;

	/**
	 * 根据用户信息获取挂牌级别
	 * 
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkEnum> loadEnums(UserDetailWrapper userDetail)
			throws ApplicationAccessException;
	
	public List<DaPipelineCompanyInfo> loadCompanysByNotice(DaPipelineCompanyInfo pipeLine,UserDetailWrapper userDetail,Pagination pagination) throws ApplicationAccessException;

	public List<DaIndustryParameter> loadTradeTypesForCompany(UserDetailWrapper userDetail) throws ApplicationAccessException ;
	public List<DaPipeRollcallCompany> loadRollcallCompaniesByCompany(DaPipelineCompanyInfo pipeLine, Pagination pagination)
	throws ApplicationAccessException;
	
	//public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException;
	/**
	 * 根据用户信息获取督办单位数据
	 * 
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkArea> loadAreas(UserDetailWrapper userDetail)
			throws ApplicationAccessException;
}
