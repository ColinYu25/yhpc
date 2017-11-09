package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.nbyhpc.domain.model.DaCreditManage;
import com.safetys.nbyhpc.domain.persistence.iface.CreditManagePersistenceIface;

public class CreditManagePersistenceImpl implements CreditManagePersistenceIface {
	private PersistenceDao<DaCreditManage> persistenceDao;

	public List<DaCreditManage> loadDaCreditManage(String hql, Object[] params)
	throws ApplicationAccessException {
		
		return this.persistenceDao.executeHQLQuery(hql, params);
	}

	public void setPersistenceDao(PersistenceDao<DaCreditManage> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
