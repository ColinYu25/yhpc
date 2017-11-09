package com.safetys.nbyhpc.facade.iface;

import java.util.List;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipeCompanyQuarterReprot;
import com.safetys.nbyhpc.domain.model.Statistic;

/**
 * @(#) PipeCompanyQuarterReportFacadeIface.java
 * @date 2012-11-21
 * @author lisl
 * @copyright (c) 2012 Safetys.cn All rights reserved.
 * 
 */
public interface PipeCompanyQuarterReportFacadeIface {
	
	public void update(DaPipeCompanyQuarterReprot entity) throws Exception ;
	
	public void delete(DaPipeCompanyQuarterReprot entity) throws Exception ;
	
	public DaPipeCompanyQuarterReprot findById(DaPipeCompanyQuarterReprot entity) throws Exception ;
	
	public List<DaPipeCompanyQuarterReprot> find(DaPipeCompanyQuarterReprot entity, Pagination pagination) throws Exception ;
	
	public void save(DaPipeCompanyQuarterReprot entity) throws Exception ;
	
	public List<DaPipeCompanyQuarterReprot> find(DaPipeCompanyQuarterReprot entity, DaCompany company, Pagination pagination) throws Exception;
	
	public Statistic loadReportByCompany(UserDetailWrapper userDetail, Statistic st) throws ApplicationAccessException;
	
	public Boolean checkIsSaveed(Statistic statistic, Long companyId) throws Exception;
}
