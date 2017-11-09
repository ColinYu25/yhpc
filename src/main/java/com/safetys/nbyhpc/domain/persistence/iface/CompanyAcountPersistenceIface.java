package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyAcount;

public interface CompanyAcountPersistenceIface {
	public Long createCompanyAcount(DaCompanyAcount daCompanyAcount)
			throws ApplicationAccessException;

	public void updateCompanyAcount(DaCompanyAcount daCompanyAcount)
			throws ApplicationAccessException;

	public void mergeCompanyAcount(DaCompanyAcount daCompanyAcount)
			throws ApplicationAccessException;

	public void deleteCompanyAcount(DaCompanyAcount daCompanyAcount)
			throws ApplicationAccessException;

	public List<DaCompanyAcount> loadCompanyAcounts(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public DaCompanyAcount loadCompanyAcount(DaCompanyAcount daCompanyAcount)
			throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;
}
