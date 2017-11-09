package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaDanger;


public interface DangerPersistenceIface {
	
	public Long createDanger(DaDanger danger) throws ApplicationAccessException;
	
	public void updateDanger(DaDanger danger) throws ApplicationAccessException;
	
	public void mergeDanger(DaDanger danger) throws ApplicationAccessException;
	
	public void deleteDanger(DaDanger danger) throws ApplicationAccessException;
	
	public List<DaDanger> loadDangers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaDanger loadDanger(DaDanger danger) throws ApplicationAccessException;
	
	public long countCriteria(DetachedCriteriaProxy detachedCriteriaProxy) throws ApplicationAccessException;
	
}