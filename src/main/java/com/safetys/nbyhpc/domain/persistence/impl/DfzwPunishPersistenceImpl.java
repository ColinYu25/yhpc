package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.Date;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjDfzwPunish;
import com.safetys.nbyhpc.domain.persistence.iface.DfzwPunishPersistenceIface;

public class DfzwPunishPersistenceImpl implements DfzwPunishPersistenceIface {

	private PersistenceDao<ZjDfzwPunish> persistenceDao;

	public long create(ZjDfzwPunish entity) throws ApplicationAccessException {
		return (Long) persistenceDao.save(entity);
	}

	public void delete(ZjDfzwPunish entity) throws ApplicationAccessException {
		entity = load(entity.getId());
		entity.setModifyTime(new Date());
		entity.setDeleted(true);
		persistenceDao.merge(entity);// 设置假删除
		// persistenceDao.delete(entity);
	}

	public ZjDfzwPunish load(long id) throws ApplicationAccessException {
		return persistenceDao.get(ZjDfzwPunish.class, id);
	}

	public List<ZjDfzwPunish> load(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if (pagination == null) {
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		} else {
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		}
	}

	public void update(ZjDfzwPunish entity) throws ApplicationAccessException {
		persistenceDao.update(entity);
	}

	public void merge(ZjDfzwPunish entity) throws ApplicationAccessException {
		persistenceDao.merge(entity);
	}

	public void setPersistenceDao(PersistenceDao<ZjDfzwPunish> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
