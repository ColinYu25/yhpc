package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaTrouble;


public interface TroublePersistenceIface {
	
	public Long createTrouble(DaTrouble trouble) throws ApplicationAccessException;
	
	public void updateTrouble(DaTrouble trouble) throws ApplicationAccessException;
	
	public void mergeTrouble(DaTrouble trouble) throws ApplicationAccessException;
	
	public void deleteTrouble(DaTrouble trouble) throws ApplicationAccessException;
	
	public List<DaTrouble> loadTroubles(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaTrouble loadTrouble(DaTrouble trouble) throws ApplicationAccessException;
}