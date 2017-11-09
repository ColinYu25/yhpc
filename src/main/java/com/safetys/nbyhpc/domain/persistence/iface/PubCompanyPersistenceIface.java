package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.TCoreCompany;

public interface PubCompanyPersistenceIface {
	public Long createCompany(TCoreCompany company)
			throws ApplicationAccessException;

	public void updateCompany(TCoreCompany company)
			throws ApplicationAccessException;

	public void mergeCompany(TCoreCompany company)
			throws ApplicationAccessException;

	public void deleteCompany(TCoreCompany company)
			throws ApplicationAccessException;

	public List<TCoreCompany> loadCompanies(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public TCoreCompany loadCompany(TCoreCompany company)
			throws ApplicationAccessException;

	public void executeHQLUpdate(String hql) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;
	
	public int executeSQLUpdate(final String sql);

	public int executeSQLUpdate(final String sql, final Object[] values);
}
