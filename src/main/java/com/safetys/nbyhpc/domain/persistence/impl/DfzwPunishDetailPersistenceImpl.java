package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.Date;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjDfzwPunishDetail;
import com.safetys.nbyhpc.domain.persistence.iface.DfzwPunishDetailPersistenceIface;

public class DfzwPunishDetailPersistenceImpl implements DfzwPunishDetailPersistenceIface {

	private PersistenceDao<ZjDfzwPunishDetail> persistenceDao;

	public long create(ZjDfzwPunishDetail entity) throws ApplicationAccessException {
		return (Long) persistenceDao.save(entity);
	}

	public void delete(ZjDfzwPunishDetail entity) throws ApplicationAccessException {
		entity = load(entity.getId());
		entity.setModifyTime(new Date());
		entity.setDeleted(true);
		persistenceDao.merge(entity);// 设置假删除
		// persistenceDao.delete(entity);
	}

	public void deleteTrue(ZjDfzwPunishDetail entity) throws ApplicationAccessException {
		persistenceDao.delete(entity);
	}

	public ZjDfzwPunishDetail load(long id) throws ApplicationAccessException {
		return persistenceDao.get(ZjDfzwPunishDetail.class, id);
	}

	public List<ZjDfzwPunishDetail> load(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if (pagination == null) {
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		} else {
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		}
	}

	public void update(ZjDfzwPunishDetail entity) throws ApplicationAccessException {
		persistenceDao.update(entity);
	}

	public void setPersistenceDao(PersistenceDao<ZjDfzwPunishDetail> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
