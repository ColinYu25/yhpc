package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;


public interface RollcallCompanyPersistenceIface {
	
	public Long createRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException;
	
	public void updateRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException;
	
	public void mergeRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException;
	
	public void deleteRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException;
	
	public List<DaRollcallCompany> loadRollcallCompanies(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaRollcallCompany loadRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException;
	
	public void executeHQLUpdate(String hql) throws ApplicationAccessException;
}