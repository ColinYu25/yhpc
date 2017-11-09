package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyLogout;

public interface CompanyLogoutPersistenceIface {
	public Long createCompanyLogout(DaCompanyLogout daCompanyLogout)
			throws ApplicationAccessException;

	public void updateCompanyLogout(DaCompanyLogout daCompanyLogout)
			throws ApplicationAccessException;

	public void mergeCompanyLogout(DaCompanyLogout daCompanyLogout)
			throws ApplicationAccessException;

	public void deleteCompanyLogout(DaCompanyLogout daCompanyLogout)
			throws ApplicationAccessException;

	public List<DaCompanyLogout> loadCompanyLogouts(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public DaCompanyLogout loadCompanyLogout(DaCompanyLogout daCompanyLogout)
			throws ApplicationAccessException;
	
	public ResultSet findBySql(String sql) throws ApplicationAccessException;
	
	public void updateBySql(String sql)  throws ApplicationAccessException;

}
