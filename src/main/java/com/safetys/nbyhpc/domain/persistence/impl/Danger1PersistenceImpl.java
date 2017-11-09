package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaDanger1;
import com.safetys.nbyhpc.domain.persistence.iface.Danger1PersistenceIface;

public class Danger1PersistenceImpl implements Danger1PersistenceIface {
	private PersistenceDao<DaDanger1> persistenceDao;

	public Long createDanger(DaDanger1 danger) throws ApplicationAccessException {
		return (Long) persistenceDao.save(danger);
	}

	public void deleteDanger(DaDanger1 danger) throws ApplicationAccessException {
		danger = loadDanger(danger);
		danger.setDeleted(true);
		persistenceDao.merge(danger);
	}

	public DaDanger1 loadDanger(DaDanger1 danger) throws ApplicationAccessException {
		return persistenceDao.get(DaDanger1.class, danger.getId());
	}

	public List<DaDanger1> loadDangers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		List<DaDanger1> list = null;

		try {
			if (pagination != null)
				list = persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
			else
				list = persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("=========="+list.get(0));

		return list;
	}

	public void updateDanger(DaDanger1 danger) throws ApplicationAccessException {
		persistenceDao.update(danger);
	}

	public void mergeDanger(DaDanger1 danger) throws ApplicationAccessException {
		persistenceDao.merge(danger);
	}

	public void setPersistenceDao(PersistenceDao<DaDanger1> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public List<DaDanger1> findPageByHQL(String hql, Pagination pagination) throws ApplicationAccessException {

		List<DaDanger1> list = persistenceDao.findPageByHQL(hql, pagination);
		return list;
	}

	public ResultSet findPageBySQL(final String sql) throws ApplicationAccessException {

		ResultSet res = persistenceDao.findBySql(sql);
		return res;
	}
}
