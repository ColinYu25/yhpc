package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallCompany;


public interface PipeRollcallCompanyPersistenceIface {
	
	public Long createRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException;
	
	public void updateRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException;
	
	public void mergeRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException;
	
	public void deleteRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException;
	
	public List<DaPipeRollcallCompany> loadRollcallCompanies(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaPipeRollcallCompany loadRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException;
	
	public void executeHQLUpdate(String hql) throws ApplicationAccessException;
}