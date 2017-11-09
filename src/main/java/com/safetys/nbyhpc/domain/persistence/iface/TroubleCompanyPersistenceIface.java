package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaTroubleCompany;


public interface TroubleCompanyPersistenceIface {
	
	public Long createTroubleCompany(DaTroubleCompany troubleCompany) throws ApplicationAccessException;
	
	public void updateTroubleCompany(DaTroubleCompany troubleCompany) throws ApplicationAccessException;
	
	public void mergeTroubleCompany(DaTroubleCompany troubleCompany) throws ApplicationAccessException;
	
	public void deleteTroubleCompany(DaTroubleCompany troubleCompany) throws ApplicationAccessException;
	
	public List<DaTroubleCompany> loadTroubleCompanys(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaTroubleCompany loadTroubleCompany(DaTroubleCompany troubleCompany) throws ApplicationAccessException;
}