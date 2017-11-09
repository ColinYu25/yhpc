package com.safetys.nbyhpc.domain.persistence.impl;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.Department;
import com.safetys.nbyhpc.domain.persistence.iface.DepartmentPersistenceIface;

public class DepartmentPersistenceImpl implements DepartmentPersistenceIface {
	private PersistenceDao<Department> persistenceDao;
	public Department loadDepartment(DetachedCriteriaProxy detachedCriteriaProxy) throws ApplicationAccessException {
		if (persistenceDao.findAllByCriteria(detachedCriteriaProxy).size() > 0) {
			return (Department) persistenceDao.findAllByCriteria(detachedCriteriaProxy).get(0);
		} else {
			return null;
		}

	}
	public PersistenceDao<Department> getPersistenceDao() {
		return persistenceDao;
	}



	public void setPersistenceDao(PersistenceDao<Department> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
