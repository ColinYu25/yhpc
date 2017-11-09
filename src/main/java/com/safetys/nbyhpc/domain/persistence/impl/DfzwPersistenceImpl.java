package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.Date;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjDfzwReport;
import com.safetys.nbyhpc.domain.persistence.iface.DfzwPersistenceIface;

public class DfzwPersistenceImpl implements DfzwPersistenceIface {

	private PersistenceDao<ZjDfzwReport> persistenceDao;

	public long create(ZjDfzwReport entity) throws ApplicationAccessException {
		return (Long) persistenceDao.save(entity);
	}

	public void delete(ZjDfzwReport entity) throws ApplicationAccessException {
		entity = load(entity.getId());
		entity.setModifyTime(new Date());
		entity.setDeleted(true);
		persistenceDao.merge(entity);// 设置假删除
		// persistenceDao.delete(entity);
	}

	public ZjDfzwReport load(long id) throws ApplicationAccessException {
		return persistenceDao.get(ZjDfzwReport.class, id);
	}

	public List<ZjDfzwReport> load(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if (pagination == null) {
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		} else {
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		}
	}

	public void update(ZjDfzwReport entity) throws ApplicationAccessException {
		persistenceDao.update(entity);
	}

	public void merge(ZjDfzwReport entity) throws ApplicationAccessException {
		persistenceDao.merge(entity);
	}

	public void setPersistenceDao(PersistenceDao<ZjDfzwReport> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
