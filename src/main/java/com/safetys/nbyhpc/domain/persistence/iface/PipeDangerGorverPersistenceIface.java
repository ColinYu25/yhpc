package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeDangerGorver;


public interface PipeDangerGorverPersistenceIface {
	
	public Long createDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException;
	
	public void updateDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException;
	
	public void mergeDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException;
	
	public void deleteDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException;
	
	public List<DaPipeDangerGorver> loadDangerGorvers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaPipeDangerGorver loadDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException;
}