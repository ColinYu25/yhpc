package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaFile;
import com.safetys.nbyhpc.domain.persistence.iface.FilePersistenceIface;

public class FilePersistenceImpl implements FilePersistenceIface{

	private PersistenceDao<DaFile> persistenceDao;

	public Long createFile(DaFile file) throws ApplicationAccessException {
		
		return (Long)persistenceDao.save(file);
	}

	public void deleteFile(DaFile file) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		
		return persistenceDao.findBySql(sql);
	}

	public List<DaFile> loadFiles(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void mergeFile(DaFile file) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		
	}

	public void updateFile(DaFile file) throws ApplicationAccessException {
		persistenceDao.update(file);
	}

	public PersistenceDao<DaFile> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaFile> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public DaFile loadFile(DaFile file) throws ApplicationAccessException {
		return persistenceDao.load(file.getClass(), file.getId());
	}
	
}
