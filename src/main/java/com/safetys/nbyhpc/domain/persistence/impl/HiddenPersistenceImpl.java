package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.dao.PersistenceDao2;
import com.safetys.framework.dao.criterion.DetachedCriteriaProxy2;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.nbyhpc.domain.model.SynErrorIds;
import com.safetys.nbyhpc.domain.model.SyncNo;
import com.safetys.nbyhpc.domain.model.VDangerEx;
import com.safetys.nbyhpc.domain.model.VNormalDangerEx;
import com.safetys.nbyhpc.domain.persistence.iface.HiddenPersistenceIface;

public class HiddenPersistenceImpl implements HiddenPersistenceIface {
	private PersistenceDao persistenceDao;
	private PersistenceDao2 persistenceDao2;

	public List<VDangerEx> getVDangerExList(
			DetachedCriteriaProxy2 detachedCriteriaProxy)
			throws ApplicationAccessException {
		return persistenceDao2.findAllByCriteria(detachedCriteriaProxy);
	}

	public List<VNormalDangerEx> getVNormalDangerExList(
			DetachedCriteriaProxy2 detachedCriteriaProxy)
			throws ApplicationAccessException {
		return persistenceDao2.findAllByCriteria(detachedCriteriaProxy);
	}
	
	public List<SynErrorIds> getSynErrorIdsList(
			DetachedCriteriaProxy2 detachedCriteriaProxy)
			throws ApplicationAccessException {
		return persistenceDao2.findAllByCriteria(detachedCriteriaProxy);
	}

	public SyncNo getSyncNo(DetachedCriteriaProxy2 detachedCriteriaProxy)
			throws ApplicationAccessException {
		List<SyncNo> syncNoList = persistenceDao2
				.findAllByCriteria(detachedCriteriaProxy);
		if (syncNoList != null && syncNoList.size() > 0) {
			return syncNoList.get(0);
		} else {
			return null;
		}
	}

	public int executeSQLUpdate(final String sql) {
		return executeSQLUpdate(sql, null);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public int executeSQLUpdate(final String sql, final Object[] values) {
		return persistenceDao.executeSQLUpdate(sql, values);
	}

	public void saveOrUpdateSyncNo(SyncNo SyncNo)
			throws ApplicationAccessException {
		persistenceDao.saveOrUpdate(SyncNo);

	}

	public void saveOrUpdateSynErrorIds(SynErrorIds synErrorIds)
			throws ApplicationAccessException {
		persistenceDao.saveOrUpdate(synErrorIds);

	}

	/**
	 * @return the persistenceDao2
	 */
	public PersistenceDao2 getPersistenceDao2() {
		return persistenceDao2;
	}

	/**
	 * @param persistenceDao2
	 *            the persistenceDao2 to set
	 */
	public void setPersistenceDao2(PersistenceDao2 persistenceDao2) {
		this.persistenceDao2 = persistenceDao2;
	}

	/**
	 * @return the persistenceDao
	 */
	public PersistenceDao getPersistenceDao() {
		return persistenceDao;
	}

	/**
	 * @param persistenceDao
	 *            the persistenceDao to set
	 */
	public void setPersistenceDao(PersistenceDao persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
