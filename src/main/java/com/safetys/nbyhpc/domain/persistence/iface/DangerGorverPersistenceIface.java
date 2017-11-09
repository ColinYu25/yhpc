package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaDangerGorver;
import com.safetys.nbyhpc.domain.model.Department;


public interface DangerGorverPersistenceIface {
	
	public Long createDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException;
	
	public void updateDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException;
	
	public void mergeDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException;
	
	public void deleteDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException;
	
	public List<DaDangerGorver> loadDangerGorvers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaDangerGorver loadDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException;
	

}