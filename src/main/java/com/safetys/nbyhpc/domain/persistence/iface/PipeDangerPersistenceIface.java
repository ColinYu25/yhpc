package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;


public interface PipeDangerPersistenceIface {
	
	public Long createDanger(DaPipeDanger danger) throws ApplicationAccessException;
	
	public void updateDanger(DaPipeDanger danger) throws ApplicationAccessException;
	
	public void mergeDanger(DaPipeDanger danger) throws ApplicationAccessException;
	
	public void deleteDanger(DaPipeDanger danger) throws ApplicationAccessException;
	
	public List<DaPipeDanger> loadDangers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaPipeDanger loadDanger(DaPipeDanger danger) throws ApplicationAccessException;
}