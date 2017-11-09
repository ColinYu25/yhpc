package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;
import com.safetys.nbyhpc.domain.persistence.iface.NomalDangerPersistenceIface;

public class NomalDangerPersistenceImpl implements NomalDangerPersistenceIface {

	private PersistenceDao<DaNomalDanger> persistenceDao;
	
	public Long createNomalDanger(DaNomalDanger daNomalDanger)
			throws ApplicationAccessException {
		
		return (Long)persistenceDao.save(daNomalDanger);
	}

	public void deleteNomalDanger(DaNomalDanger daNomalDanger)
			throws ApplicationAccessException {
		persistenceDao.delete(daNomalDanger);

	}

	public DaNomalDanger loadNomalDanger(DaNomalDanger daNomalDanger)
			throws ApplicationAccessException {
		
		return (DaNomalDanger)persistenceDao.load(DaNomalDanger.class, daNomalDanger.getId());
	}

	public List<DaNomalDanger> loadNomalDangers(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if(pagination!=null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		
	}

	@Override
	public List<DaNomalDanger> loadNomalDangers(String hql, Pagination pagination, Object[] params) {
		return persistenceDao.findPageByHQL(hql, pagination, params);
	}

	public void mergeNomalDanger(DaNomalDanger daNomalDanger)
			throws ApplicationAccessException {
		persistenceDao.merge(daNomalDanger);

	}

	public void updateNomalDanger(DaNomalDanger daNomalDanger)
			throws ApplicationAccessException {
		persistenceDao.update(daNomalDanger);

	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public int executeUpdate(String hql) throws ApplicationAccessException {
		 return persistenceDao.executeHQLUpdate(hql);
		
	}
	
	public PersistenceDao<DaNomalDanger> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaNomalDanger> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
