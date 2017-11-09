package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallFile;
import com.safetys.nbyhpc.domain.persistence.iface.PipeRollcallFilePersistenceIface;

public class PipeRollcallFilePersistenceImpl implements PipeRollcallFilePersistenceIface{

	private PersistenceDao<DaPipeRollcallFile> persistenceDao;
	
	public Long createRollcallFile(DaPipeRollcallFile rollcallFile) throws ApplicationAccessException {
		return (Long)persistenceDao.save(rollcallFile);
	}

	public void deleteRollcallFile(DaPipeRollcallFile rollcallFile) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public DaPipeRollcallFile loadRollcallFile(DaPipeRollcallFile rollcallFile) throws ApplicationAccessException {
		
		return persistenceDao.load(rollcallFile.getClass(), rollcallFile.getId());
	}

	public List<DaPipeRollcallFile> loadRollcallFiles(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination!=null){
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		}else{
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		}
		
	}

	public void mergeRollcallFile(DaPipeRollcallFile rollcallFile) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		
	}

	public void updateRollcallFile(DaPipeRollcallFile rollcallFile) throws ApplicationAccessException {
		persistenceDao.update(rollcallFile);
	}

	public PersistenceDao<DaPipeRollcallFile> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaPipeRollcallFile> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}


}
