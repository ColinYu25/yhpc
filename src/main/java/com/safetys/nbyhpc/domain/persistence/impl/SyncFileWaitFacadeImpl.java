package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.nbyhpc.domain.model.SyncFileWait;
import com.safetys.nbyhpc.domain.persistence.iface.SyncFileWaitFacadeIface;

public class SyncFileWaitFacadeImpl implements SyncFileWaitFacadeIface {

	private PersistenceDao<SyncFileWait> persistenceDao;
	
	
	public void add(String realFilePath, String filePath) {
		SyncFileWait f = new SyncFileWait();
		f.setFilePath(filePath);
		f.setRealFilePath(realFilePath);
		persistenceDao.save(f);
	}

	public List<SyncFileWait> findAll() {
		try {
			return persistenceDao.executeHQLQuery(" from SyncFileWait where deleted = false ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<SyncFileWait>();
	}

	public void delete(SyncFileWait wait){
		persistenceDao.delete(wait);
	}
	
	public void setPersistenceDao(PersistenceDao<SyncFileWait> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
