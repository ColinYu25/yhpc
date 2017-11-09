package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;

/**
 * @(#) RollcallCompanyFacadeIface.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public interface RollcallCompanyFacadeIface {

	/**
	 * 创建一个隐患挂牌信息
	 * 
	 * @param rollcallCompany
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Long createRollcallCompany(DaRollcallCompany rollcallCompany)
			throws ApplicationAccessException;

	/**
	 * 加载一个隐患挂牌信息
	 * 
	 * @param rollcallCompany
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaRollcallCompany loadRollcallCompany(DaRollcallCompany rollcallCompany)
			throws ApplicationAccessException;

	public DaDanger loadDanger(DaDanger danger) throws ApplicationAccessException;
	
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException;
	
	
	/**
	 * 修改一个隐患挂牌信息
	 * 
	 * @param rollcallCompany
	 * @throws ApplicationAccessException
	 */
	public void updateRollcallCompany(DaRollcallCompany rollcallCompany)
			throws ApplicationAccessException;

	public void updateRollcallCompanyForNotice(DaRollcallCompany rollcallCompany)
	throws ApplicationAccessException;
	
	/**
	 * 加载隐患挂牌信息
	 * 
	 * @param rollcallCompany
	 * @throws ApplicationAccessException
	 */
	public List<DaRollcallCompany> loadRollcallCompanies(Long dangerId)
			throws ApplicationAccessException;

	/**
	 * 隐患挂牌分级列表
	 * 
	 * @param rollcallCompany
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaRollcallCompany> loadRollcallCompaniesOfLevel(
			DaRollcallCompany rollcallCompany,DaCompany company, Pagination pagination,
			UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	public List<DaRollcallCompany> loadRollcallCompaniesOfLevel(DaCompany company,
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
	
	public List<DaCompany> loadCompanysByNotice(DaCompany company,UserDetailWrapper userDetail,Pagination pagination) throws ApplicationAccessException;

	public List<DaIndustryParameter> loadTradeTypesForCompany(UserDetailWrapper userDetail) throws ApplicationAccessException ;
	public List<DaRollcallCompany> loadRollcallCompaniesByCompany(DaCompany company, Pagination pagination)
	throws ApplicationAccessException;
	
	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException;
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
