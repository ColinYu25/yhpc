package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.Date;
import java.util.List;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjBigTrouble;
import com.safetys.nbyhpc.domain.persistence.iface.BigTroublePersistenceIface;

public class BigTroublePersistenceImpl implements BigTroublePersistenceIface {
	public PersistenceDao<ZjBigTrouble> persistenceDao;

	public void createBigTrouble(ZjBigTrouble bigTrouble) {
		persistenceDao.save(bigTrouble);
	}

	public List<ZjBigTrouble> loadBigTroubles(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination paginatio) {
		if(paginatio==null){
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		}
		return persistenceDao.findPageByCriteria(detachedCriteriaProxy, paginatio);
	}

	public void deleteBigTrouble(ZjBigTrouble bigTrouble, FkUser fkUser) throws ApplicationAccessException {
		bigTrouble = loadBigTrouble(bigTrouble.getId());
		bigTrouble.setDeleted(true);
		bigTrouble.setModifyTime(new Date());
		bigTrouble.setUserId(fkUser);
		persistenceDao.merge(bigTrouble);
		//手动设置假删除，防止恶意访问url删除数据后，无证据可查，亦无法恢复。
//		persistenceDao.delete(bigTrouble);
	}

	
	public void updateBigTrouble(ZjBigTrouble bigTrouble){
		persistenceDao.update(bigTrouble);
	}
	
	public ZjBigTrouble loadBigTrouble(long id)
			throws ApplicationAccessException {
		return persistenceDao.get(ZjBigTrouble.class, id);
	}
	
	public void updateBigTroubleOut(ZjBigTrouble bigTrouble){
		persistenceDao.merge(bigTrouble);
	}	
	
	public List<ZjBigTrouble> loadBigTroublesForStatistic(DetachedCriteriaProxy detachedCriteriaProxy) {
		return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public PersistenceDao<ZjBigTrouble> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<ZjBigTrouble> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
