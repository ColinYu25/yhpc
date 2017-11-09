package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeNomalDanger;
import com.safetys.nbyhpc.domain.persistence.iface.PipeNomalDangerPersistenceIface;

public class PipeNomalDangerPersistenceImpl implements PipeNomalDangerPersistenceIface {

	private PersistenceDao<DaPipeNomalDanger> persistenceDao;
	
	public Long createNomalDanger(DaPipeNomalDanger nomalDanger)
			throws ApplicationAccessException {
		return (Long)persistenceDao.save(nomalDanger);
	}

	public void deleteNomalDanger(DaPipeNomalDanger nomalDanger)
			throws ApplicationAccessException {
		persistenceDao.delete(nomalDanger);

	}

	public DaPipeNomalDanger loadNomalDanger(DaPipeNomalDanger nomalDanger)
			throws ApplicationAccessException {
		
		return (DaPipeNomalDanger)persistenceDao.load(DaPipeNomalDanger.class, nomalDanger.getId());
	}

	public List<DaPipeNomalDanger> loadNomalDangers(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if(pagination!=null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		
	}

	public void mergeNomalDanger(DaPipeNomalDanger nomalDanger)
			throws ApplicationAccessException {
		persistenceDao.merge(nomalDanger);
	}

	public void updateNomalDanger(DaPipeNomalDanger nomalDanger)
			throws ApplicationAccessException {
		persistenceDao.update(nomalDanger);

	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public int executeUpdate(String hql) throws ApplicationAccessException {
		 return persistenceDao.executeHQLUpdate(hql);
		
	}
	
	public PersistenceDao<DaPipeNomalDanger> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaPipeNomalDanger> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}




}
