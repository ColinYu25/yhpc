package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.nbyhpc.domain.model.DaSecurityManage;
import com.safetys.nbyhpc.domain.persistence.iface.SecurityManagePersistenceIface;

public class SecurityManagePersistenceImpl implements SecurityManagePersistenceIface {
	private PersistenceDao<DaSecurityManage> persistenceDao;


	public List<DaSecurityManage> loadDaSecurityManage(String hql,Object[] params)
			throws ApplicationAccessException {
		return persistenceDao.executeHQLQuery(hql, params);
	}

	public void updateDaSecurityManage(DaSecurityManage securityManage)
			throws ApplicationAccessException {
		persistenceDao.merge(securityManage);
	}

	public void executeHQLUpdate(String hql) throws ApplicationAccessException{
		persistenceDao.executeHQLUpdate(hql);
	}

	public void setPersistenceDao(PersistenceDao<DaSecurityManage> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
