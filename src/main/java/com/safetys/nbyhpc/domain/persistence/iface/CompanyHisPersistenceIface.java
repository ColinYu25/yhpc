package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyHis;

public interface CompanyHisPersistenceIface {

	public Long createCompany(DaCompanyHis company) throws ApplicationAccessException;

	public void updateCompany(DaCompanyHis company) throws ApplicationAccessException;

	public void mergeCompany(DaCompanyHis company) throws ApplicationAccessException;

	public void deleteCompany(DaCompanyHis company) throws ApplicationAccessException;

	public List<DaCompanyHis> loadCompanies(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;

	public DaCompanyHis loadCompany(DaCompanyHis company) throws ApplicationAccessException;

	public void executeHQLUpdate(String hql) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

	public DaCompanyHis loadCompanyInfo(DetachedCriteriaProxy detachedCriteriaProxy) throws ApplicationAccessException;

	public void executeSQLUpdate(String sql) throws ApplicationAccessException;
}