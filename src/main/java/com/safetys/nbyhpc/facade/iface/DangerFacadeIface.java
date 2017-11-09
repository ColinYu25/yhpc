package com.safetys.nbyhpc.facade.iface;

import java.sql.SQLException;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.Statistic;
/**
 * @(#) DangerFacadeIface.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public interface DangerFacadeIface {
	
	/**
	 * 创建一个重大隐患信息
	 * @param danger
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void createDanger(DaDanger danger) throws ApplicationAccessException;
	
	public List<DaCompany> loadCompanyIdByNum(String num) throws ApplicationAccessException;
	
	/**
	 * 加载一个重大隐患信息
	 * @param danger
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaDanger loadDanger(DaDanger danger) throws ApplicationAccessException;
	
	public List<DaIndustryParameter> loadIndustrysForDanger() throws ApplicationAccessException;
	
	/**
	 * 修改一个重大隐患信息
	 * @param danger
	 * @throws ApplicationAccessException
	 */
	public void updateDanger(DaDanger danger) throws ApplicationAccessException;
	
	/**
	 * 删除重大隐患信息(可批量)
	 * @param danger
	 * @throws ApplicationAccessException
	 */
	public void deleteDangers(String ids) throws ApplicationAccessException;
	
	public void deleteDanger(DaDanger danger) throws ApplicationAccessException;
	
	/**
	 * 加载重大隐患信息，或分页或不分
	 * @param danger
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaDanger> loadDangers(DaDanger danger,DaCompany company,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	/**
	 * 加载拥有重大隐患的企业列表
	 * @param danger
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	
	public List<DaIndustryParameter> loadTradeTypesForCompany(UserDetailWrapper userDetail) throws ApplicationAccessException;
	public List<DaDanger> loadDangersOfCompanies(DaDanger danger,DaCompany company,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException;
	/**
	 * 查询总共有几条重大隐患
	 * @param danger
	 * @param company
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public long loadDangers4Count(DaDanger danger,DaCompany company,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	
	/**
	 * 加载企业信息
	 * @param company
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException;
	
	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException;
	
	public List<Statistic> loadDangersOfCompaniesBySql(DaDanger danger,DaCompany company,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	/**
	 * 查询企业是否提交季度报表
	 */
	public long  loadCompanyQuarterlyAccount(DaCompany company)throws ApplicationAccessException;

	/**
	 * 根据工商注册号查询企业信息
	 * @param company
	 * @return DaCompany
	 * @throws Exception
	 */
	public List<DaCompany> loadCompanyByRegNum(DaCompany company)throws ApplicationAccessException;

	
	/**
	 * 修改录入时间超过7天的未治理重大隐患治理状态为已治理
	 * 
	 * @param days
	 * @throws SQLException 
	 */
	public void updateDangerRepaired(int days) throws ApplicationAccessException;
}
