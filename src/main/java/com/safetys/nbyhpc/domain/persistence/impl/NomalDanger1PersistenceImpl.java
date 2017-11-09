package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaNomalDanger1;
import com.safetys.nbyhpc.domain.persistence.iface.NomalDanger1PersistenceIface;

public class NomalDanger1PersistenceImpl implements NomalDanger1PersistenceIface {

	private PersistenceDao<DaNomalDanger1> persistenceDao;
	
	public Long createNomalDanger(DaNomalDanger1 DaNomalDanger1)
			throws ApplicationAccessException {
		
		return (Long)persistenceDao.save(DaNomalDanger1);
	}

	public void deleteNomalDanger(DaNomalDanger1 DaNomalDanger1)
			throws ApplicationAccessException {
		persistenceDao.delete(DaNomalDanger1);

	}

	public DaNomalDanger1 loadNomalDanger(DaNomalDanger1 DaNomalDanger1)
			throws ApplicationAccessException {
		
		return (DaNomalDanger1)persistenceDao.load(DaNomalDanger1.class, DaNomalDanger1.getId());
	}

	public List<DaNomalDanger1> loadNomalDangers(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if(pagination!=null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		
	}

	public void mergeNomalDanger(DaNomalDanger1 DaNomalDanger1)
			throws ApplicationAccessException {
		persistenceDao.merge(DaNomalDanger1);

	}

	public void updateNomalDanger(DaNomalDanger1 DaNomalDanger1)
			throws ApplicationAccessException {
		persistenceDao.update(DaNomalDanger1);

	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public int executeUpdate(String hql) throws ApplicationAccessException {
		 return persistenceDao.executeHQLUpdate(hql);
		
	}
	
	public PersistenceDao<DaNomalDanger1> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaNomalDanger1> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}




}
