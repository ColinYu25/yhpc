package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.Statistic;
/**
 * @(#) DangerAction.java
 * @date 2012-11-16
 * @author lisl
 * @copyright (c) 2012 Safetys.cn All rights reserved.
 * 
 */
public interface PipeDangerFacadeIface {
	
	/**
	 * 创建一个重大隐患信息
	 * @param danger
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void createDanger(DaPipeDanger danger) throws ApplicationAccessException;
	
	/**
	 * 加载一个重大隐患信息
	 * @param danger
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaPipeDanger loadDanger(DaPipeDanger danger) throws ApplicationAccessException;
	
	public List<DaIndustryParameter> loadIndustrysForDanger() throws ApplicationAccessException;
	
	/**
	 * 修改一个重大隐患信息
	 * @param danger
	 * @throws ApplicationAccessException
	 */
	public void updateDanger(DaPipeDanger danger) throws ApplicationAccessException;
	
	/**
	 * 删除重大隐患信息(可批量)
	 * @param danger
	 * @throws ApplicationAccessException
	 */
	public void deleteDangers(String ids) throws ApplicationAccessException;
	
	public void deleteDanger(DaPipeDanger danger) throws ApplicationAccessException;
	
	/**
	 * 加载重大隐患信息，或分页或不分
	 * @param danger
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaPipeDanger> loadDangers(DaPipeDanger danger,DaPipelineInfo pipeLine,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	/**
	 * 加载拥有重大隐患的企业列表
	 * @param danger
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	
	public List<DaIndustryParameter> loadTradeTypesForCompany(UserDetailWrapper userDetail) throws ApplicationAccessException;
	public List<DaPipeDanger> loadDangersOfCompanies(DaPipeDanger danger, DaCompany com,DaPipelineInfo pipeLine,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	/**
	 * 加载企业信息
	 * @param company
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaPipelineInfo loadCompany(DaPipelineInfo pipeLine) throws ApplicationAccessException;
	
	public List<Statistic> loadDangersOfCompaniesBySql(DaPipeDanger danger,DaPipelineInfo pipeLine,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	/**
	 * 查询企业是否提交季度报表
	 */
	public long  loadCompanyQuarterlyAccount(DaPipelineInfo pipeLine)throws ApplicationAccessException;

	/**
	 * 根据工商注册号查询企业信息
	 * @param company
	 * @return DaPipelineInfo
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadCompanyByRegNum(DaPipelineInfo pipeLine)throws ApplicationAccessException;

}
