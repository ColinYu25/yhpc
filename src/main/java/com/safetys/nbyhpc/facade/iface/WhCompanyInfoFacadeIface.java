package com.safetys.nbyhpc.facade.iface;


import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.WhAccident;
import com.safetys.nbyhpc.domain.model.WhCompanyInfo;

/**
 * @(#) CompanyFacadeIface.java
 * @date 2009-07-29
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public interface WhCompanyInfoFacadeIface {
	
	static int REPORTED = 1;
	
	static int UN_REPORT = 0;
	
	public long create(WhCompanyInfo entity, List<WhAccident> accidents) throws Exception ;
	
	public void update(WhCompanyInfo entity, List<WhAccident> accidents) throws Exception ;
	
	public void delete(WhCompanyInfo entity) throws Exception ;
	
	public WhCompanyInfo findById(long id) throws Exception ;
	
	public List<WhCompanyInfo> find(WhCompanyInfo entity, Pagination pagination) throws Exception;
	
	public WhCompanyInfo findByUserId(long userId, int year);
	
	public DaCompany loadCompanyByUserId(Long userId);
	
	/**
	 * 根据企业的用户id，查找该企业的危化企业上报信息。
	 * @param userId
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<WhCompanyInfo> loadCompanyInfoByUserId(long userId, Pagination pagination) throws Exception;
	
	/**
	 * 判断是否已上报
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean hasReported(long id) throws Exception;
	
	/**
	 * 上报
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean report(long id) throws Exception;
	
	/**
	 * 退回
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean back(long id) throws Exception;
	
	/**
	 * 根据区域id，加载区域名称
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String getAreaNameById(long id) throws Exception;
	
	/**
	 * 统计
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public List statistic(String year) throws Exception;

	public boolean hasExistedByYear(int year, long companyId, long id) throws Exception;
	
	/**
	 * 根据区域代码，查找已上报的企业
	 * @param areaCode
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<DaCompany> loadReportedCompanyList(Long areaCode, int year, Pagination pagination) throws Exception;
	
	/**
	 * 根据区域代码，查找未上报的企业
	 * @param areaCode
	 * @param year
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<DaCompany> loadUnreportCompanyList(Long areaCode, int year , Pagination pagination) throws Exception;
	
}
