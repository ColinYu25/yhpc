package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaImageFile;
import com.safetys.nbyhpc.domain.persistence.iface.ImageFilePersistenceIface;

public class ImageFilePersistenceImpl implements ImageFilePersistenceIface {
	private PersistenceDao<DaImageFile> persistenceDao;


	public void createFile(DaImageFile daImageFile){
		persistenceDao.save(daImageFile);
	}
	
	public void updateFile(DaImageFile daImageFile) throws ApplicationAccessException {
		persistenceDao.update(daImageFile);
	}
	
	public void deleteFile(DaImageFile daImageFile, boolean deleted)
			throws ApplicationAccessException {
		if (deleted)
			persistenceDao.delete(daImageFile);
		else
			deleteFile(daImageFile);
	}

	public void deleteFile(DaImageFile daImageFile) throws ApplicationAccessException {
		daImageFile = loadDaImageFile(daImageFile);
		daImageFile.setDeleted(true);
		persistenceDao.merge(daImageFile);
	}

	public List<DaImageFile> loadFiles(DetachedCriteriaProxy detachedCriteriaProxy, 
			Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}
	
	public DaImageFile loadDaImageFile(DaImageFile daImageFile) throws ApplicationAccessException{
		return (DaImageFile) persistenceDao.load(DaImageFile.class, daImageFile.getId());
	}

	public void mergeDaImageFile(DaImageFile daImageFile)
			throws ApplicationAccessException {
		persistenceDao.merge(daImageFile);
	}

	public void setPersistenceDao(PersistenceDao<DaImageFile> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public DaImageFile findById(long id) throws Exception {
		return persistenceDao.get(DaImageFile.class, id);
	}
	
}
