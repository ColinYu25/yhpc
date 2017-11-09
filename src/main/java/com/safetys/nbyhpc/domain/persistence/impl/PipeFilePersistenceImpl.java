package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeFile;
import com.safetys.nbyhpc.domain.persistence.iface.PipeFilePersistenceIface;

public class PipeFilePersistenceImpl implements PipeFilePersistenceIface{

	private PersistenceDao<DaPipeFile> persistenceDao;

	public Long createFile(DaPipeFile file) throws ApplicationAccessException {
		
		return (Long)persistenceDao.save(file);
	}

	public void deleteFile(DaPipeFile file) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		
		return persistenceDao.findBySql(sql);
	}

	public List<DaPipeFile> loadFiles(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void mergeFile(DaPipeFile file) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		
	}

	public void updateFile(DaPipeFile file) throws ApplicationAccessException {
		persistenceDao.update(file);
	}

	public PersistenceDao<DaPipeFile> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaPipeFile> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public DaPipeFile loadFile(DaPipeFile file) throws ApplicationAccessException {
		return persistenceDao.load(file.getClass(), file.getId());
	}
	
}
