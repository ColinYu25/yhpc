package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaDept;
import com.safetys.nbyhpc.domain.persistence.iface.DeptPersistenceIface;

public class DeptPersistenceImpl implements DeptPersistenceIface {
	private PersistenceDao<DaDept> persistenceDao;

	public Long createDept(DaDept dept) throws ApplicationAccessException {
		return (Long) persistenceDao.save(dept);
	}

	public void deleteDept(DaDept dept) throws ApplicationAccessException {
		dept = loadDept(dept);
		dept.setDeleted(true);
		persistenceDao.merge(dept);
	}

	public DaDept loadDept(DaDept dept) throws ApplicationAccessException {
		return persistenceDao.get(DaDept.class, dept.getId());
	}

	public List<DaDept> loadDeptes(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateDept(DaDept dept) throws ApplicationAccessException {
		persistenceDao.update(dept);
	}

	public void mergeDept(DaDept dept) throws ApplicationAccessException {
		persistenceDao.merge(dept);
	}	

	public void setPersistenceDao(PersistenceDao<DaDept> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
