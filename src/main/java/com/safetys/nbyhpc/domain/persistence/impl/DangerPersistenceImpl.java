package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.persistence.iface.DangerPersistenceIface;

public class DangerPersistenceImpl implements DangerPersistenceIface {
	private PersistenceDao<DaDanger> persistenceDao;


	public Long createDanger(DaDanger danger) throws ApplicationAccessException {
		return (Long) persistenceDao.save(danger);
	}

	public void deleteDanger(DaDanger danger) throws ApplicationAccessException {
		
		danger = loadDanger(danger);
		danger.setDeleted(true);	
		danger.setIsSynchro(1);
		Calendar cal=Calendar.getInstance();    
		danger.setDeleteTime(cal.getTime());
		ResultSet   res   =persistenceDao.findBySql("select t.id   from  zj_send  t where ( t.type=1  or  t.type=2)  and   t.is_deleted=0   and  t.report_month='"+danger.getCreateTime().toString().substring(0,7)+"'");	
		
		try {
			if (res.next()){
				danger.setFlag(1l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		persistenceDao.merge(danger);
	}

	public DaDanger loadDanger(DaDanger danger) throws ApplicationAccessException {
		return persistenceDao.get(DaDanger.class, danger.getId());
	}

	public List<DaDanger> loadDangers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		List<DaDanger> list = null;
		try{
			if(pagination != null)
				list = persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
			else
				list = persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		}catch (Exception e){
			e.printStackTrace();
		}
		return list;
	}

	public long countCriteria(DetachedCriteriaProxy detachedCriteriaProxy) throws ApplicationAccessException {
		List result = persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		if (result != null && !result.isEmpty()) {
			return Long.valueOf(String.valueOf(result.get(0)));
		}
		return 0;
	}

	public void updateDanger(DaDanger danger) throws ApplicationAccessException {
		persistenceDao.update(danger);
	}

	public void mergeDanger(DaDanger danger) throws ApplicationAccessException {
		persistenceDao.merge(danger);
	}	

	public void setPersistenceDao(PersistenceDao<DaDanger> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
