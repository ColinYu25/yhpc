package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPointType;
import com.safetys.nbyhpc.domain.persistence.iface.PointTypePersistenceIface;

public class PointTypePersistenceImpl implements PointTypePersistenceIface {
	private PersistenceDao<DaPointType> persistenceDao;

	public Long createPointType(DaPointType pointType) throws ApplicationAccessException {
		return (Long) persistenceDao.save(pointType);
	}

	public void deletePointType(DaPointType pointType) throws ApplicationAccessException {
		pointType = loadPointType(pointType);
		pointType.setDeleted(true);
		persistenceDao.merge(pointType);
	}

	public DaPointType loadPointType(DaPointType pointType) throws ApplicationAccessException {
		return persistenceDao.get(DaPointType.class, pointType.getId());
	}

	public List<DaPointType> loadPointTypes(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updatePointType(DaPointType pointType) throws ApplicationAccessException {
		persistenceDao.update(pointType);
	}

	public void mergePointType(DaPointType pointType) throws ApplicationAccessException {
		persistenceDao.merge(pointType);
	}	

	public void setPersistenceDao(PersistenceDao<DaPointType> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
