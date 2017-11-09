package com.safetys.nbyhpc.facade.iface;


import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyQuarterReprot;


/**
 * @(#) CompanyFacadeIface.java
 * @date 2009-07-29
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public interface CompanyQuarterReportFacadeIface {
	
	public long create(DaCompanyQuarterReprot entity) throws Exception ;
	
	public void update(DaCompanyQuarterReprot entity) throws Exception ;
	
	public void delete(DaCompanyQuarterReprot entity) throws Exception ;
	
	public DaCompanyQuarterReprot findById(DaCompanyQuarterReprot entity) throws Exception ;
	
	public List<DaCompanyQuarterReprot> find(DaCompanyQuarterReprot entity, Pagination pagination) throws Exception ;
	
	public void save(DaCompanyQuarterReprot entity) throws Exception ;
	
	
	public List<DaCompanyQuarterReprot> find(DaCompanyQuarterReprot entity, DaCompany company, Pagination pagination) throws Exception;
	
}
