package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeCompanyQuarterReprot;


public interface PipeCompanyQuarterReportPersistenceIface {
	public Long create(DaPipeCompanyQuarterReprot entity)
			throws ApplicationAccessException;
	
	public void update(DaPipeCompanyQuarterReprot entity) throws ApplicationAccessException;
	
	public void delete(DaPipeCompanyQuarterReprot entity)
			throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

	public DaPipeCompanyQuarterReprot findById(Long id)
			throws ApplicationAccessException;

	public List<DaPipeCompanyQuarterReprot> find(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;
	
	public List find(String hql, Object[] params, Pagination page);
}
