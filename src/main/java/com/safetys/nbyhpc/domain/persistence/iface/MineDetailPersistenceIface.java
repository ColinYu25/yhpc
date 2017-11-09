package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjMineReportDetail;

public interface MineDetailPersistenceIface {
	
	public long createMineReportDetail(ZjMineReportDetail mineReportDetail) 
				throws ApplicationAccessException;
	
	public void updateMineReportDetail(ZjMineReportDetail mineReportDetail)
				throws ApplicationAccessException;
	
	public void deleteMineReportDetail(ZjMineReportDetail mineReportDetail)
				throws ApplicationAccessException;
	
	public List<ZjMineReportDetail> loadMineReportDetails(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;
	
	public ZjMineReportDetail loadMineReportDetail(long id) 
				throws ApplicationAccessException;
	
}
