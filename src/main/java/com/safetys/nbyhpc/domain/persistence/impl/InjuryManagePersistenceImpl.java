package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.nbyhpc.domain.model.DaInjuryManage;
import com.safetys.nbyhpc.domain.persistence.iface.InjuryManagePersistenceIface;

public class InjuryManagePersistenceImpl implements InjuryManagePersistenceIface {
	private PersistenceDao<DaInjuryManage> persistenceDao;


	public List<DaInjuryManage> loadDaInjuryManage(String hql,Object[] params)
			throws ApplicationAccessException {
		return persistenceDao.executeHQLQuery(hql, params);
	}

	public void updateDaInjuryManage(DaInjuryManage injuryManage)
			throws ApplicationAccessException {
		persistenceDao.merge(injuryManage);
	}

	public void executeHQLUpdate(String hql) throws ApplicationAccessException{
		persistenceDao.executeHQLUpdate(hql);
	}

	public void setPersistenceDao(PersistenceDao<DaInjuryManage> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
