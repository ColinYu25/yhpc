package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.dao.criterion.DetachedCriteriaProxy2;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.TCompany;

public interface TCompanyPersistenceIface { 

	public Long createCompany(TCompany company) throws ApplicationAccessException;

	public void updateCompany(TCompany company) throws ApplicationAccessException;

	public void mergeCompany(TCompany company) throws ApplicationAccessException;

	public void deleteCompany(TCompany company) throws ApplicationAccessException;

	public TCompany loadCompanies(DetachedCriteriaProxy detachedCriteriaProxy) throws ApplicationAccessException; 

	public TCompany loadCompany(TCompany company) throws ApplicationAccessException;

	public void executeHQLUpdate(String hql) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

	public TCompany loadCoreCompany(TCompany company) throws ApplicationAccessException;
	
	public List<TCompany> loadCompanies(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public TCompany loadCompanies2(DetachedCriteriaProxy2 detachedCriteriaProxy) throws ApplicationAccessException;
	
	public List<TCompany> loadCompanies2(DetachedCriteriaProxy2 detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
}