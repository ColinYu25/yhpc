package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaAcceptance;
import com.safetys.nbyhpc.domain.persistence.iface.AcceptancePersistenceIface;

public class AcceptancePersistenceImpl implements AcceptancePersistenceIface{

	private PersistenceDao<DaAcceptance> persistenceDao;
	
	public void setPersistenceDao(PersistenceDao<DaAcceptance> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public Long createAcceptance(DaAcceptance acceptance) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteAcceptance(DaAcceptance acceptance) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public DaAcceptance loadAcceptance(DaAcceptance acceptance) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DaAcceptance> loadAcceptances(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public void mergeAcceptance(DaAcceptance acceptance) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		
	}

	public void updateAcceptance(DaAcceptance acceptance) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		
	}

	public PersistenceDao<DaAcceptance> getPersistenceDao() {
		return persistenceDao;
	}

}
