package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjMineReport;

public interface MinePersistenceIface {
	
	public long createMineReport(ZjMineReport mineReport) 
			throws ApplicationAccessException;
	
	public void updateMineReport(ZjMineReport mineReport)
			throws ApplicationAccessException;
	
	public void deleteMineReport(ZjMineReport mineReport)
			throws ApplicationAccessException;
	
	public List<ZjMineReport> loadMineReports(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;
	
	public ZjMineReport loadMineReport(long id) 
			throws ApplicationAccessException;
	
	public void mergeMineReport(ZjMineReport mineReport)
			throws ApplicationAccessException;
}
