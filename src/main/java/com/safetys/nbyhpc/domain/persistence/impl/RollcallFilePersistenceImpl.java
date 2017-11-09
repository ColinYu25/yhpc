package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaRollcallFile;
import com.safetys.nbyhpc.domain.persistence.iface.RollcallFilePersistenceIface;

public class RollcallFilePersistenceImpl implements RollcallFilePersistenceIface{

	private PersistenceDao<DaRollcallFile> persistenceDao;
	
	public Long createRollcallFile(DaRollcallFile rollcallFile) throws ApplicationAccessException {
		return (Long)persistenceDao.save(rollcallFile);
	}

	public void deleteRollcallFile(DaRollcallFile rollcallFile) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public DaRollcallFile loadRollcallFile(DaRollcallFile rollcallFile) throws ApplicationAccessException {
		
		return persistenceDao.load(rollcallFile.getClass(), rollcallFile.getId());
	}

	public List<DaRollcallFile> loadRollcallFiles(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination!=null){
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		}else{
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		}
		
	}

	public void mergeRollcallFile(DaRollcallFile rollcallFile) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		
	}

	public void updateRollcallFile(DaRollcallFile rollcallFile) throws ApplicationAccessException {
		persistenceDao.update(rollcallFile);
	}

	public PersistenceDao<DaRollcallFile> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaRollcallFile> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}


}
