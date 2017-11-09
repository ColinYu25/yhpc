package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.Date;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjDfzwReportDetail;
import com.safetys.nbyhpc.domain.persistence.iface.DfzwDetailPersistenceIface;

public class DfzwDetailPersistenceImpl implements DfzwDetailPersistenceIface {

	private PersistenceDao<ZjDfzwReportDetail> persistenceDao;

	public long create(ZjDfzwReportDetail entity) throws ApplicationAccessException {
		return (Long) persistenceDao.save(entity);
	}

	public void delete(ZjDfzwReportDetail entity) throws ApplicationAccessException {
		entity = load(entity.getId());
		entity.setModifyTime(new Date());
		entity.setDeleted(true);
		persistenceDao.merge(entity);// 设置假删除
	}

	public void deleteTrue(ZjDfzwReportDetail entity) throws ApplicationAccessException {
		persistenceDao.delete(entity);
	}

	public ZjDfzwReportDetail load(long id) throws ApplicationAccessException {
		return persistenceDao.get(ZjDfzwReportDetail.class, id);
	}

	public List<ZjDfzwReportDetail> load(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if (pagination == null) {
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		} else {
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		}
	}

	public void update(ZjDfzwReportDetail entity) throws ApplicationAccessException {
		persistenceDao.update(entity);
	}

	public void setPersistenceDao(PersistenceDao<ZjDfzwReportDetail> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
