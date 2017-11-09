package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.dao.criterion.DetachedCriteriaProxy2;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompany;

public interface CompanyPersistenceIface {

	public Long createCompany(DaCompany company) throws ApplicationAccessException;

	public void updateCompany(DaCompany company) throws ApplicationAccessException;

	public void mergeCompany(DaCompany company) throws ApplicationAccessException;

	public void deleteCompany(DaCompany company) throws ApplicationAccessException;

	public List<DaCompany> loadCompanies(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;

	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException;

	public void executeHQLUpdate(String hql) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

	public DaCompany loadCompanyInfo(DetachedCriteriaProxy detachedCriteriaProxy) throws ApplicationAccessException;

	public void executeSQLUpdate(String sql) throws ApplicationAccessException;

	public List<DaCompany> loadCompanyByUserId(String Hql) throws ApplicationAccessException;
	
	public List<DaCompany> loadCompanies2(DetachedCriteriaProxy2 detachedCriteriaProxy) throws ApplicationAccessException ;
}